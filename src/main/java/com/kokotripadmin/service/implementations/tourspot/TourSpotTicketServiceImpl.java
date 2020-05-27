package com.kokotripadmin.service.implementations.tourspot;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.tourspot.*;
import com.kokotripadmin.dto.ticket.TicketPriceDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketImageDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketInfoDto;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicket;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketImage;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketInfo;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketPrice;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.exception.tour_spot.ticket.*;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.entityinterfaces.TicketTypeEntityService;
import com.kokotripadmin.service.entityinterfaces.TourSpotEntityService;
import com.kokotripadmin.service.entityinterfaces.TourSpotTicketEntityService;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotTicketService;
import com.kokotripadmin.spec.tourspot.TourSpotTicketSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourSpotTicketServiceImpl implements TourSpotTicketService, TourSpotTicketEntityService {


    private final TourSpotTicketDao tourSpotTicketDao;
    private final TourSpotTicketInfoDao tourSpotTicketInfoDao;
    private final TourSpotTicketImageDao tourSpotTicketImageDao;

    private final TourSpotEntityService        tourSpotEntityService;
    private final TicketTypeEntityService      ticketTypeEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final BucketService bucketService;

    private final ModelMapper modelMapper;
    private final Convert convert;

    private final String TOUR_SPOT_TICKET_IMAGE_DIRECTORY = "tour-spot/ticket/image";

    public TourSpotTicketServiceImpl(TourSpotTicketDao tourSpotTicketDao,
                                     TourSpotTicketImageDao tourSpotTicketImageDao,
                                     TourSpotEntityService tourSpotEntityService,
                                     TicketTypeEntityService ticketTypeEntityService,
                                     SupportLanguageEntityService supportLanguageEntityService,
                                     TourSpotTicketInfoDao tourSpotTicketInfoDao,
                                     BucketService bucketService,
                                     ModelMapper modelMapper, Convert convert) {

        this.tourSpotTicketDao = tourSpotTicketDao;
        this.tourSpotTicketImageDao = tourSpotTicketImageDao;
        this.tourSpotEntityService = tourSpotEntityService;
        this.ticketTypeEntityService = ticketTypeEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.tourSpotTicketInfoDao = tourSpotTicketInfoDao;
        this.bucketService = bucketService;
        this.modelMapper = modelMapper;
        this.convert = convert;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicket findEntityById(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException {
        return tourSpotTicketDao.findById(tourSpotTicketId).orElseThrow(TourSpotTicketNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketInfo findInfoEntityByIdAndSupportLanguageId(Integer tourSpotTicketId,
                                                                     Integer supportLanguageId)
    throws TourSpotTicketInfoNotFoundException {
        return tourSpotTicketInfoDao.findOne(TourSpotTicketSpec.findInfoByIdAndSupportLanguageId(tourSpotTicketId, supportLanguageId))
                                    .orElseThrow(TourSpotTicketInfoNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketDto findById(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException {
        TourSpotTicket tourSpotTicket = findEntityById(tourSpotTicketId);
        return modelMapper.map(tourSpotTicket, TourSpotTicketDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketDto findByIdInDetail(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException {
        TourSpotTicket tourSpotTicket = findEntityById(tourSpotTicketId);
        return convert.tourSpotTicketToDtoInDetail(tourSpotTicket);
    }


    @Override
    @Transactional
    public Integer save(TourSpotTicketDto tourSpotTicketDto)
    throws TourSpotTicketNotFoundException, SupportLanguageNotFoundException,
           TourSpotNotFoundException, TourSpotTicketNameDuplicateException, TourSpotInfoNotFoundException,
           TicketTypeNotFoundException, TourSpotTicketInfoAlreadyExistsException, TourSpotTicketInfoNotFoundException {

        TourSpotTicket tourSpotTicket;
        if (tourSpotTicketDto.getId() == null) tourSpotTicket = create(tourSpotTicketDto);
        else tourSpotTicket = update(tourSpotTicketDto);
        tourSpotTicketDao.save(tourSpotTicket);
        return tourSpotTicket.getId();
    }


    private boolean existsByTourSpotIdAndName(Integer tourSpotId, String tourSpotTicketName) {
        long count = tourSpotTicketDao.count(TourSpotTicketSpec.findByTourSpotIdAndName(tourSpotId, tourSpotTicketName));
        return count > 0;
    }

    private TourSpotTicket create(TourSpotTicketDto tourSpotTicketDto)
    throws TourSpotInfoNotFoundException, TicketTypeNotFoundException, TourSpotNotFoundException,
           TourSpotTicketNameDuplicateException, TourSpotTicketInfoAlreadyExistsException,
           TourSpotTicketNotFoundException,
           SupportLanguageNotFoundException {


        Integer tourSpotId = tourSpotTicketDto.getTourSpotId();

        if (existsByTourSpotIdAndName(tourSpotId, tourSpotTicketDto.getName()))
            throw new TourSpotTicketNameDuplicateException(tourSpotTicketDto.getName());

        TourSpot tourSpot = tourSpotEntityService.findEntityById(tourSpotId);

        TourSpotTicket tourSpotTicket = modelMapper.map(tourSpotTicketDto, TourSpotTicket.class);
        tourSpotTicket.setTourSpot(tourSpot);

        setTicketPriceList(tourSpotTicket, tourSpotTicketDto.getTicketPriceDtoList());

        TourSpotTicketInfoDto tourSpotTicketInfoDto = modelMapper.map(tourSpotTicketDto, TourSpotTicketInfoDto.class);
        tourSpotTicketInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        TourSpotTicketInfo tourSpotTicketInfo = createInfo(tourSpotTicket, tourSpotTicketInfoDto);
        tourSpotTicket.getTourSpotTicketInfoList().add(tourSpotTicketInfo);

        return tourSpotTicket;
    }


    private void setTicketPriceList(TourSpotTicket tourSpotTicket, List<TicketPriceDto> ticketPriceDtoList)
    throws TicketTypeNotFoundException {
        for (TicketPriceDto ticketPriceDto : ticketPriceDtoList) {

            TicketType ticketType = ticketTypeEntityService.findEntityById(ticketPriceDto.getTicketTypeId());

            TourSpotTicketPrice tourSpotTicketPrice = modelMapper.map(ticketPriceDto, TourSpotTicketPrice.class);
            tourSpotTicketPrice.setTourSpotTicket(tourSpotTicket);
            tourSpotTicketPrice.setTicketType(ticketType);

            if (tourSpotTicketPrice.isRepPrice())
                tourSpotTicket.setRepPrice(tourSpotTicketPrice.getPrice());
            tourSpotTicket.getTourSpotTicketPriceList().add(tourSpotTicketPrice);
        }
    }


    private TourSpotTicket update(TourSpotTicketDto tourSpotTicketDto)
    throws TourSpotTicketNotFoundException, TourSpotTicketInfoNotFoundException, TourSpotTicketNameDuplicateException,
           TicketTypeNotFoundException {

        TourSpotTicket tourSpotTicket = findEntityById(tourSpotTicketDto.getId());

        if (!tourSpotTicket.getName().equals(tourSpotTicketDto.getName())
                && existsByTourSpotIdAndName(tourSpotTicket.getTourSpotId(), tourSpotTicketDto.getName()))
            throw new TourSpotTicketNameDuplicateException(tourSpotTicketDto.getName());

        tourSpotTicket.clone(tourSpotTicketDto);
        tourSpotTicket.getTourSpotTicketPriceList().clear();
        setTicketPriceList(tourSpotTicket, tourSpotTicketDto.getTicketPriceDtoList());

        updateTicketInfos(tourSpotTicket);
        tourSpotTicket.setUpdatedAt(new Date());

        return tourSpotTicket;
    }

    @Override
    @Transactional
    public Integer delete(Integer tourSpotTicketId) throws TourSpotTicketNotFoundException {
        TourSpotTicket tourSpotTicket = findEntityById(tourSpotTicketId);
        Integer tourSpotId = tourSpotTicket.getTourSpotId();
        tourSpotTicketDao.delete(tourSpotTicket);
        return tourSpotId;
    }


    //  =================================== IMAGE ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketImage findImageByImageId(Integer tourSpotTicketImageId) throws TourSpotTicketImageNotFoundException {
        return tourSpotTicketImageDao.findById(tourSpotTicketImageId).orElseThrow(TourSpotTicketImageNotFoundException::new);
    }


    @Override
    @Transactional
    public Integer saveImage(TourSpotTicketImageDto tourSpotTicketImageDto)
    throws TourSpotTicketNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {
        TourSpotTicket tourSpot = findEntityById(tourSpotTicketImageDto.getTourSpotTicketId());
        String bucketKey = TOUR_SPOT_TICKET_IMAGE_DIRECTORY + "/" + tourSpot.getName() + "/" + tourSpotTicketImageDto.getName();

        if (tourSpotTicketImageDao.count(TourSpotTicketSpec.findImageByIdAndImageBucketKey(tourSpot.getId(), bucketKey)) > 0)
            throw new ImageDuplicateException(tourSpotTicketImageDto.getName());

        TourSpotTicketImage tourSpotTicketImage = modelMapper.map(tourSpotTicketImageDto, TourSpotTicketImage.class);
        tourSpotTicketImage.setTourSpotTicket(tourSpot);
        tourSpotTicketImage.setBucketKey(bucketKey);
        tourSpotTicketImageDao.save(tourSpotTicketImage);

        bucketService.uploadImage(bucketKey, tourSpotTicketImageDto.getName(), tourSpotTicketImageDto.getMultipartFile());
        return tourSpotTicketImage.getId();
    }

    @Override
    @Transactional
    public void updateRepImage(Integer imageId) throws TourSpotTicketImageNotFoundException {
        TourSpotTicketImage newRepImage = findImageByImageId(imageId);
        List<TourSpotTicketImage> repImageList = tourSpotTicketImageDao.findAll(TourSpotTicketSpec.findImageByIdAndRepImage(newRepImage.getTourSpotTicketId(),
                                                                                                          true));
        for (TourSpotTicketImage repImage : repImageList) {
            if (newRepImage != repImage) repImage.setRepImage(false);
        }
        newRepImage.setRepImage(true);
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<TourSpotTicketImage> tourSpotTicketImageList = tourSpotTicketImageDao.findAll(TourSpotTicketSpec.findImageByIds(imageIdList));
        HashMap<Integer, TourSpotTicketImage> tourSpotTicketImageHashMap = tourSpotTicketImageList.stream()
                                                                                .collect(Collectors
                                                                                                 .toMap(TourSpotTicketImage::getId,
                                                                                                          tourSpotTicketImage -> tourSpotTicketImage,
                                                                                                        (oKey, nKey) -> oKey,
                                                                                                        HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            TourSpotTicketImage tourSpotTicketImage = tourSpotTicketImageHashMap.get(imageId);
            if (tourSpotTicketImage != null) {
                tourSpotTicketImage.setOrder(order);
                order++;
            }
        }
        tourSpotTicketImageDao.saveAll(tourSpotTicketImageList);
    }


    @Override
    @Transactional
    public void deleteImage(Integer tourSpotTicketImageId)
    throws TourSpotTicketImageNotFoundException, RepImageNotDeletableException {
        TourSpotTicketImage tourSpotTicketImage = findImageByImageId(tourSpotTicketImageId);
        if (tourSpotTicketImage.isRepImage())
            throw new RepImageNotDeletableException();
        bucketService.deleteImage(tourSpotTicketImage.getBucketKey());
        tourSpotTicketImageDao.delete(tourSpotTicketImage);
    }

//  ========================================== INFO ===========================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketInfo findInfoEntityByInfoId(Integer tourSpotTicketInfoId)
    throws TourSpotTicketInfoNotFoundException {
        return tourSpotTicketInfoDao.findById(tourSpotTicketInfoId).orElseThrow(TourSpotTicketInfoNotFoundException::new);
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public TourSpotTicketInfoDto saveInfo(TourSpotTicketInfoDto tourSpotTicketInfoDto)
    throws TourSpotTicketNotFoundException, TourSpotInfoNotFoundException, SupportLanguageNotFoundException,
           TourSpotTicketInfoAlreadyExistsException, TourSpotTicketInfoNotEditableException,
           TourSpotTicketInfoNotFoundException {

        TourSpotTicketInfo tourSpotTicketInfo;

        if (tourSpotTicketInfoDto.getId() == null) tourSpotTicketInfo = createInfo(null, tourSpotTicketInfoDto);
        else tourSpotTicketInfo = updateInfo(tourSpotTicketInfoDto);
        tourSpotTicketInfoDao.save(tourSpotTicketInfo);
        return modelMapper.map(tourSpotTicketInfo, TourSpotTicketInfoDto.class);
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer tourSpotTicketId, Integer supportLanguageId) {
        long count = tourSpotTicketInfoDao.count(TourSpotTicketSpec.findInfoByIdAndSupportLanguageId(tourSpotTicketId,
                                                                                                     supportLanguageId));
        return count > 0;
    }

    private TourSpotTicketInfo createInfo(TourSpotTicket tourSpotTicket, TourSpotTicketInfoDto tourSpotTicketInfoDto)
    throws TourSpotTicketNotFoundException, SupportLanguageNotFoundException, TourSpotTicketInfoAlreadyExistsException,
           TourSpotInfoNotFoundException {

        if (tourSpotTicket == null)
            tourSpotTicket = findEntityById(tourSpotTicketInfoDto.getTourSpotTicketId());

        Integer supportLanguageId = tourSpotTicketInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(tourSpotTicket.getId(), supportLanguageId))
            throw new TourSpotTicketInfoAlreadyExistsException(tourSpotTicket.getName(), supportLanguage.getName());

        TourSpotInfo tourSpotInfo =
                tourSpotEntityService.findInfoEntityByIdAndSupportLanguageId(tourSpotTicket.getTourSpotId(),
                                                                             supportLanguageId);

        TourSpotTicketInfo tourSpotTicketInfo = modelMapper.map(tourSpotTicketInfoDto, TourSpotTicketInfo.class);
        tourSpotTicketInfo.setForeignEntities(tourSpotInfo, tourSpotTicket, supportLanguage);
        tourSpotTicketInfo.denormalize(tourSpotTicket);

        return tourSpotTicketInfo;
    }


    private TourSpotTicketInfo updateInfo(TourSpotTicketInfoDto tourSpotTicketInfoDto)
    throws TourSpotTicketInfoNotFoundException, TourSpotTicketInfoNotEditableException {

        TourSpotTicketInfo tourSpotTicketInfo = findInfoEntityByInfoId(tourSpotTicketInfoDto.getId());

        if (tourSpotTicketInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotTicketInfoNotEditableException();

        tourSpotTicketInfo.clone(tourSpotTicketInfoDto);
        tourSpotTicketInfo.setUpdatedAt(new Date());

        return tourSpotTicketInfo;
    }

    private void updateTicketInfos(TourSpotTicket tourSpotTicket) throws TourSpotTicketInfoNotFoundException {

        List<TourSpotTicketInfo> tourSpotTicketInfoList = tourSpotTicket.getTourSpotTicketInfoList();
        for (TourSpotTicketInfo tourSpotTicketInfo : tourSpotTicketInfoList)
            tourSpotTicketInfo.denormalize(tourSpotTicket);

        TourSpotTicketInfo tourSpotTicketInfoInKorean =
                tourSpotTicketInfoList.stream().filter(t -> t.getSupportLanguageId()
                                                             .equals(SupportLanguageEnum.Korean.getId()))
                                      .findFirst()
                                      .orElseThrow(TourSpotTicketInfoNotFoundException::new);

        tourSpotTicketInfoInKorean.clone(tourSpotTicket);
    }

    @Override
    @Transactional
    public void deleteInfo(Integer tourSpotTicketInfoId)
    throws TourSpotTicketInfoNotFoundException, TourSpotTicketInfoNotDeletableException {
        TourSpotTicketInfo tourSpotTicketInfo = findInfoEntityByInfoId(tourSpotTicketInfoId);
        if (tourSpotTicketInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotTicketInfoNotDeletableException();
        tourSpotTicketInfoDao.delete(tourSpotTicketInfo);
    }

}
