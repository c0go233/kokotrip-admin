package com.kokotripadmin;

public class ToDo {

//    TODO: famous-region service fix the create and update famous-region and update enabled and name to famous-region-info
//    TODO: famous-region info edit form remove enabled
//    TODO: cityInfoSpec and change cityService and dao
//    TODO: dayofweek and tradinghourtype service and dao create
//    TODO: we dont need datatablesoutputviewmodel because we can just convert datatablesoutputdto to json
//    TODO: remove enabled in tour_spot or other form page and put delete button some needs to do extra work for deletion like
//          tour_spot needs to subtract its tags in city_tag
//    TODO: tour_spot edit page fix showing list of famous-region under city
//    TODO: create and add activity review table
//    TODO: considering adding tagname in tourspot info
//    TODO: considering int or Integer for datatype int in mysql
//    TODO: exception message considering espeically for tourspotinfonotfoundexception
//    TODO: some entities have getenabled and isEnabled
//    TODO: error page naming and controller should return error page
//    TODO: lombok getter setter plugin
//    TODO: info dto info viewmodel replace supportLanguage with suuportlangagename

//    TODO: 17.03.2020 - Add tag_name on tour_spot_info, add tour_spot in dummy activity table for searching purpose of tag id with tag_name=""empty
//    TODO:              edit theme or tag name then refresh tag_name on tour_spot_info and activity_info
//    TODO: famous_region table rename to region and rename @Column and @JoinColumn
//    TODO: check if city has region before insert tour_spot check the relationship between city and region
//    TODO: tour_spot change city then it should update city_id or region_id of tour_spot_info as well
//    TODO: create table for review for tourspotticket
//    TODO: when updating an entity, update all related denormalization fields of other tables
//    TODO: dto and viewmodel list and other dto or viewmodel name make them same
//    TODO: tickettype detail implementation
//    TODO: considering adding dummy tourspotinfo to activityinfo table or use unionall for searching tourspot based on categories
//    TODO: add tag detail page for tag_info
//    TODO: change onetomany list then can't intitalize even if new ArrayList().....fuck --> because of the modelmapper dto have list = null, the modelmapper maps null to the variable of Entity
//    TODO: not ot use datatbles sdk just change to my own custom code for inputs and outputs --> dto and entity has the same name then it can query on child property with foreignkey
//    TODO: tour-spot-info repiamgepaht, repimpagefiletype, // numebrofwish, average rate, numbeofrate for ticket and activity // average rate, numberofwish, numberofrate for tourspotinfo, activityinfo, ticketinfo
//    TODO: make custom converter only maps necessary fields
//    TODO: tickettypeinfo, dayofweek, not implemented
//    TODO: check how to rollback when exception thown in servicelayer



//    TODO: findInDetail info map causes n+1 query problem on supportlanague, get map of supportlangauge and map on service level refer to tourspot findByIdWithTradingHourList
//    TODO: mapping parent name like cityName in region should happen regionToDtoInDetail in convert not modelmapper because modelmapper is used edit and edit only needs parent ID to map the name because it already has the parent list
//    TODO: region dropdown should have select none option
//    TODO: setUpdatedAt(new Date()) to update() and updateINfo() in all classes
//    TODO: uncomment token code in kokotripadmin.js // uncomment meta in header of master.tag // uncomment security related config
//    TODO: download jqueryui with only sortable and draggable via custom download


//    TODO: draft for form like cityform, tourspotform, when ask add then create draft // create draft table


//    TODO: to add: bucketService.deleteImages(city.getCityImageList()); in delete
}
