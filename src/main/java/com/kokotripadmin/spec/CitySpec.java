package com.kokotripadmin.spec;

import com.kokotripadmin.entity.city.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;

public class CitySpec {

    public static Specification<City> findByEnabled(boolean cityEnabled) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(City_.id)));
            return criteriaBuilder.equal(root.get(City_.enabled), cityEnabled);
        };
    }

    public static Specification<City> findByName(String cityName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(City_.name), cityName);
        };
    }

    public static Specification<CityInfo> findInfoById(Integer cityId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(CityInfo_.cityId), cityId);
        };
    }

    public static Specification<CityInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(CityInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<CityInfo> findInfoByIdAndSupportLanguageId(Integer cityId, Integer supportLanguageId) {
        return Specification.where(findInfoById(cityId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }

    public static Specification<CityThemeRel> findThemeRelById(Integer cityId, boolean fetchThemeTagRel) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (fetchThemeTagRel) {
                root.fetch(CityThemeRel_.cityThemeTagRelList.getName(), JoinType.LEFT);
                criteriaQuery.distinct(true);
            }
            return criteriaBuilder.equal(root.get(CityThemeRel_.cityId), cityId);
        };
    }


    public static Specification<CityThemeRel> findThemeRelByThemeId(Integer themeId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(CityThemeRel_.themeId), themeId);
        };
    }

    public static Specification<CityThemeRel> findThemeRelByIdAndThemeId(Integer cityId, Integer themeId) {
        return Specification.where(findThemeRelById(cityId, false)).and(findThemeRelByThemeId(themeId));
    }

    public static Specification<CityThemeTagRel> findThemeTagRelByThemeRelId(Integer themeRelId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(CityThemeTagRel_.cityThemeRelId), themeRelId);
        };
    }

    public static Specification<CityThemeTagRel> findThemeTagRelByTagId(Integer tagId, boolean fetchThemeRel,
                                                                        boolean fetchCity) {
        return (root, criteriaQuery, criteriaBuilder) -> {


            if (fetchThemeRel) {
                Fetch<CityThemeTagRel, CityThemeRel> fetch =
                        root.fetch(CityThemeTagRel_.cityThemeRel.getName(), JoinType.LEFT);

                if (fetchCity)
                    fetch.fetch(CityThemeRel_.city.getName(), JoinType.LEFT);

                criteriaQuery.distinct(true);
            }

            return criteriaBuilder.equal(root.get(CityThemeTagRel_.tagId), tagId);
        };
    }

    public static Specification<CityThemeTagRel> findThemeTagRelByThemeRelIdAndTagId(Integer themeRelId,
                                                                                     Integer tagId) {
        return Specification.where(findThemeTagRelByThemeRelId(themeRelId))
                            .and(findThemeTagRelByTagId(tagId, false, false));
    }


    public static Specification<CityImage> findImageById(Integer cityId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(CityImage_.cityId), cityId);
    }

    public static Specification<CityImage> findImageByImageBucketKey(String cityImageBucketKey) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(CityImage_.bucketKey),
                                                                               cityImageBucketKey);
    }

    public static Specification<CityImage> findImageByIdAndImageBucketKey(Integer cityId, String cityImageBucketKey) {
        return Specification.where(findImageById(cityId)).and(findImageByImageBucketKey(cityImageBucketKey));
    }

    public static Specification<CityImage> findImageByRepImage(boolean repImage) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(CityImage_.repImage), repImage);
    }

    public static Specification<CityImage> findImageByIdAndRepImage(Integer cityId, boolean repImage) {
        return Specification.where(findImageById(cityId)).and(findImageByRepImage(repImage));
    }



}
