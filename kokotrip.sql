-- ImageEntity alt add
-- make announcement page table
-- make setting table for user
-- make advertisement page table
-- make F&Q page table
-- make customer support page table
-- decide how to display duration of ticket, in comment or having duration_type table
-- notice table for each city to be updated with latest information or alarm


-- Note
-- city search will display popup menu which has two pages, one for city selection and the other for city > famous region
-- when user select city --> city page / when user select city > famous region then --> city page with famous region filter selected
-- in city page, distance filter will have famous regions and All Region
-- photo zone will have page itself and a button to see it as tour_spot if it is also tour_spot
-- photo zone page will show the picture taken from the photo_zone
-- should have a table where you can get the size of images based on where you put your images in like in a list, detailed page, profile frame
-- when user click a city, then loading page will display the image of the city with some animation


create database if not exists `kokotrip`;


-- when users register, they should choose support language
create table `support_language`
(
  `id`           int(11)     not null auto_increment,
  `name`         varchar(50) not null unique,
  `enabled`      tinyint(1)  not null,
  `order`        int(11)     not null, -- order of display in drop-down menu
  `display_name` varchar(50) not null, -- 한국어 English, 日本語
  `created_at`   timestamp   not null default current_timestamp,
  `updated_at`   timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);


-- 0 = false | (x > 1) = true
insert into `support_language`
values (null, '한국어', 1, 1, '한국어', current_timestamp(), current_timestamp());

insert into `support_language`
values (null, '영어', 1, 1, 'English', current_timestamp(), current_timestamp());



create table `state`
(
  `id`         int(11)      not null auto_increment,
  `name`       varchar(100) not null, -- korean just for management
  `enabled`    tinyint(1)   not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);



insert into `state`
values (null, '서울특별시', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '부산광역시', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '인청광역시', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '대구광역시', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '대전광역시', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '광주광역시', 1, current_timestamp(), current_timestamp());


insert into `state`
values (null, '전라남도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '전라북도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '충청남도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '충청북도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '강원도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '경기도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '울산광역시', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '제주특별자치도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '세종특별자치시', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '경상남도', 1, current_timestamp(), current_timestamp());
insert into `state`
values (null, '경상북도', 1, current_timestamp(), current_timestamp());



use kokotrip;
select *
from region_theme_tag_rel;


# 1. distance filter will have all, all - region, regions belonging to the city, no other options like going back to state or something
# 2. If a user clicks search bar, then popup will show up with two panels, one for city and the other for regions 
# 3. city that is an independent state by itself will have dummy state like 서울특별시 -> 서울 
create table `city`
(
  `id`             int(11)         not null auto_increment,
  `name`           varchar(50)     not null,
  `enabled`        tinyint(1)      not null,
  `description`    varchar(500)    not null,
  `latitude`       decimal(18, 12) not null,
  `longitude`      decimal(18, 12) not null,
  `rep_image_path` varchar(4096),
  `state_id`       int(11)         not null,
  `created_at`     timestamp       not null default current_timestamp,
  `updated_at`     timestamp       not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_state_id_by_city` foreign key (`state_id`) references `state` (`id`)
);



insert into `city`
values (null, '서울', 1, 37.56667, 126.97806, null, 1, current_timestamp(), current_timestamp());
insert into `city`
values (null, '부산', 1, 35.17944, 129.07556, null, 2, current_timestamp(), current_timestamp());
insert into `city`
values (null, '인천', 1, 37.45639, 126.70528, null, 3, current_timestamp(), current_timestamp());
insert into `city`
values (null, '대구', 1, 35.87222, 128.60250, null, 4, current_timestamp(), current_timestamp());
insert into `city`
values (null, '대전', 1, 36.35111, 127.38500, null, 5, current_timestamp(), current_timestamp());
insert into `city`
values (null, '광주', 1, 35.15972, 126.85306, null, 6, current_timestamp(), current_timestamp());


# 1. city_image disabled will affect the rep_image_path and rep_image_file_type of this table
# 2. updating city enabled, latitude, longitude, rep_image_path, rep_image_file_type will update the denormalized fields of this table
# 3. updating city name will update the name of default city_info
create table `city_info`
(
  `id`                  int(11)         not null auto_increment,
  `name`                varchar(100)    not null,
  `enabled`             tinyint(1)      not null,
  `description`         varchar(500)    not null,
  `latitude`            decimal(18, 12) not null, -- denormalization
  `longitude`           decimal(18, 12) not null, -- denormalization
  `rep_image_path`      varchar(4096),            -- denormalization
  `city_id`             int(11)         not null,
  `support_language_id` int(11)         not null,
  `created_at`          timestamp       not null default current_timestamp,
  `updated_at`          timestamp       not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  index `enabled_support_language_id` (`enabled`, `support_language_id`),
  constraint `fk_city_id_by_city_info` foreign key (`city_id`) references `city` (`id`),
  constraint `fk_support_language_id_by_city_info` foreign key (`support_language_id`) references `support_language` (`id`)

);

insert into city_info
values (null, '서울', 1, 232.32, 342.23, null, null, 1, 1, current_timestamp(), current_timestamp());
insert into city_info
values (null, '부산', 1, 232.32, 342.23, null, null, 2, 1, current_timestamp(), current_timestamp());
insert into city_info
values (null, '인천', 1, 232.32, 342.23, null, null, 3, 1, current_timestamp(), current_timestamp());
insert into city_info
values (null, '대구', 1, 232.32, 342.23, null, null, 4, 1, current_timestamp(), current_timestamp());
insert into city_info
values (null, '대전', 1, 232.32, 342.23, null, null, 5, 1, current_timestamp(), current_timestamp());
insert into city_info
values (null, '광주', 1, 232.32, 342.23, null, null, 6, 1, current_timestamp(), current_timestamp());



create table `region`
(
  `id`             int(11)         not null auto_increment,
  `name`           varchar(50)     not null,
  `enabled`        tinyint(1)      not null,
  `description`    varchar(500)    not null,
  `latitude`       decimal(18, 12) not null,
  `longitude`      decimal(18, 12) not null,
  `rep_image_path` varchar(4096),
  `city_id`        int(11)         not null,
  `created_at`     timestamp       not null default current_timestamp,
  `updated_at`     timestamp       not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_city_id_by_region` foreign key (`city_id`) references `city` (`id`)
);

select *
from region;


# 1. If region changes its city, then it will update city_info_id
create table `region_info`
(
  `id`                  int(11)         not null auto_increment,
  `name`                varchar(100)    not null,
  `enabled`             tinyint(1)      not null,
  `description`         varchar(500)    not null,
  `latitude`            decimal(18, 12) not null, -- denormalization
  `longitude`           decimal(18, 12) not null, -- denormalization
  `rep_image_path`      varchar(4096),            -- denormalization
  `city_info_id`        int(11)         not null,
  `region_id`           int(11)         not null,
  `support_language_id` int(11)         not null,
  `created_at`          timestamp       not null default current_timestamp,
  `updated_at`          timestamp       not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_city_info_id_by_region_info` foreign key (`city_info_id`) references `city_info` (`id`),
  constraint `fk_region_id_by_region_info` foreign key (`region_id`) references `region` (`id`),
  constraint `fk_support_language_id_by_region_info` foreign key (`support_language_id`) references `support_language` (`id`)
);



# 1. the id should the same as the numeric value of day of week in java: java sunday = 1 ~ saturday = 7
# 2. duplicates of a day will be handled on client-side with if or something
create table `day_of_week`
(
  `id`         int(11)     not null auto_increment, -- works as the number for the date of week like id 1 means monday (1)
  `name`       varchar(20) not null,
  `enabled`    tinyint(1)  not null,
  `order`      int(11)     not null,
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);


insert into `day_of_week`
values (null, '일요일', 1, 0, current_timestamp(), current_timestamp());
insert into `day_of_week`
values (null, '월요일', 1, 1, current_timestamp(), current_timestamp());
insert into `day_of_week`
values (null, '화요일', 1, 2, current_timestamp(), current_timestamp());
insert into `day_of_week`
values (null, '수요일', 1, 3, current_timestamp(), current_timestamp());
insert into `day_of_week`
values (null, '목요일', 1, 4, current_timestamp(), current_timestamp());
insert into `day_of_week`
values (null, '금요일', 1, 5, current_timestamp(), current_timestamp());
insert into `day_of_week`
values (null, '토요일', 1, 6, current_timestamp(), current_timestamp());


# 1. this table will be cached on application level
create table `day_of_week_info`
(
  `id`                  int(11)   not null auto_increment,
  `name`                varchar(100),
  `enabled`             int(11)   not null,
  `order`               int(11)   not null,
  `day_of_week_id`      int(11)   not null,
  `support_language_id` int(11)   not null,
  `created_at`          timestamp not null default current_timestamp,
  `updated_at`          timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_day_of_week_id_by_day_of_week_info` foreign key (`day_of_week_id`) references `day_of_week` (`id`),
  constraint `fk_support_language_id_by_day_of_week_info` foreign key (`support_language_id`) references `support_language` (`id`)
);


# Korean
insert into `day_of_week_info`
values (null, '일요일', 1, 0, 1, 1, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, '월요일', 1, 1, 2, 1, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, '화요일', 1, 2, 3, 1, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, '수요일', 1, 3, 4, 1, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, '목요일', 1, 4, 5, 1, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, '금요일', 1, 5, 6, 1, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, '토요일', 1, 6, 7, 1, current_timestamp(), current_timestamp());


# English
insert into `day_of_week_info`
values (null, 'Sunday', 1, 0, 1, 2, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, 'Monday', 1, 1, 2, 2, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, 'Tuesday', 1, 2, 3, 2, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, 'Wednesday', 1, 3, 4, 2, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, 'Thursday', 1, 4, 5, 2, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, 'Friday', 1, 5, 6, 2, current_timestamp(), current_timestamp());
insert into `day_of_week_info`
values (null, 'Saturday', 1, 6, 7, 2, current_timestamp(), current_timestamp());


# 1. There are only open and close for trading_hour_type
create table `trading_hour_type`
(
  `id`         int(11)     not null auto_increment,
  `name`       varchar(50) not null,
  `enabled`    tinyint(1)  not null,
  `order`      int(11)     not null,
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

insert into `trading_hour_type`
values (null, '열림', 1, 0, current_timestamp(), current_timestamp());
insert into `trading_hour_type`
values (null, '휴무', 1, 1, current_timestamp(), current_timestamp());


# 1. Each support_language will have trading_hour_type = open, the value of which is "" (empty string)
create table `trading_hour_type_info`
(
  `id`                   int(11)      not null auto_increment,
  `name`                 varchar(100) not null,
  `enabled`              tinyint(1)   not null,
  `trading_hour_type_id` int(11)      not null,
  `support_language_id`  int(11)      not null,
  `created_at`           timestamp    not null default current_timestamp,
  `updated_at`           timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_trading_hour_type_id_by_trading_hour_type_info` foreign key (`trading_hour_type_id`) references `trading_hour_type` (`id`),
  constraint `fk_support_language_id_by_trading_hour_type_info` foreign key (`support_language_id`) references `support_language` (`id`)
);


# Korean
insert into `trading_hour_type_info`
values (null, '', 1, 1, 1, current_timestamp(), current_timestamp());
insert into `trading_hour_type_info`
values (null, '휴무', 1, 2, 1, current_timestamp(), current_timestamp());


# English
insert into `trading_hour_type_info`
values (null, '', 1, 1, 2, current_timestamp(), current_timestamp());
insert into `trading_hour_type_info`
values (null, 'closed', 1, 2, 2, current_timestamp(), current_timestamp());



create table `theme`
(
  `id`             int(11)      not null auto_increment,
  `name`           varchar(100) not null,
  `enabled`        tinyint(1)   not null,
  `rep_image_path` varchar(4096),
  `created_at`     timestamp    not null default current_timestamp,
  `updated_at`     timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);



create table `theme_info`
(
  `id`                  int(11)    not null auto_increment,
  `name`                varchar(100),
  `enabled`             tinyint(1) not null,
  `rep_image_path`      varchar(4096),
  `theme_id`            int(11)    not null,
  `support_language_id` int(11)    not null,
  `created_at`          timestamp  not null default current_timestamp,
  `updated_at`          timestamp  not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_theme_id_by_theme_info` foreign key (`theme_id`) references `theme` (`id`),
  constraint `fk_support_language_id_by_theme_info` foreign key (`support_language_id`) references `support_language` (`id`)
);


create table `tag`
(
  `id`             int(11)      not null auto_increment,
  `name`           varchar(100) not null,
  `enabled`        tinyint(1)   not null,
  `rep_image_path` varchar(4096),
  `theme_id`       int(11)      not null,
  `created_at`     timestamp    not null default current_timestamp,
  `updated_at`     timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_theme_id_by_tag` foreign key (`theme_id`) references `theme` (`id`)
);


create table `tag_info`
(
  `id`                  int(11)    not null auto_increment,
  `name`                varchar(100),
  `enabled`             tinyint(1) not null,
  `rep_image_path`      varchar(4096),
  `tag_id`              int(11)    not null,
  `support_language_id` int(11)    not null,
  `created_at`          timestamp  not null default current_timestamp,
  `updated_at`          timestamp  not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tag_id_by_tag_info` foreign key (`tag_id`) references `tag` (`id`),
  constraint `fk_support_language_id_by_tag_info` foreign key (`support_language_id`) references `support_language` (`id`)
);



# 1. Distance filter will have all region that belongs to the selected city and "all region" which lists all
#    tour_spot under the selected city
# 2. Listing tour_spot currently open --> is_always_open = true union all (trading_hour_type = open and open > ? and closed < ?)
# 3. tour_spot will be ordered by "popular_score", "average_rate" , "number_of_wish_list_saved"
# 4. tour_spot detail page shows nearby tour_spots --> grab tour_spots based on the same city_id or region
#    then calculate distance. A single city would have maximum of 1000 tour_spots which is reasonable amount to calculate
create table `tour_spot`
(
  `id`                        int(11)       not null auto_increment,
  `name`                      varchar(50)   not null,
  `enabled`                   tinyint(1)    not null,
  `description`               varchar(1000) not null,
  `latitude`                  decimal(18, 12),
  `longitude`                 decimal(18, 12),
  `always_open`               tinyint(1)    not null,
  `address`                   varchar(1000),
  `contact`                   varchar(1000),
  `website_link`              varchar(2048),
  `average_rate`              decimal(3, 1),          -- out of 10
  `number_of_rate`            int(11)       not null,
  `popular_score`             decimal(3, 1) not null, -- out of 10
  `number_of_wish_list_saved` int(11)       not null,
  `rep_image_path`            varchar(4096),
  `city_id`                   int(11)       not null,
  `region_id`                 int(11),
  `tag_id`                    int(11)       not null,
  `created_at`                timestamp     not null default current_timestamp,
  `updated_at`                timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_city_id_by_tour_spot` foreign key (`city_id`) references `city` (`id`),
  constraint `fk_region_id_by_tour_spot` foreign key (`region_id`) references `region` (`id`),
  constraint `fk_tag_id_by_tour_spot` foreign key (`tag_id`) references `tag` (`id`)
);



# 1. if you denormalize number_of_rate, then you should update at least two or more tour_spot_info
# 2. one to many to tour_spot_description_info
# 3. when you query tour_spot_info, you should query support_language first in the first place,
#    so if you set support_language to false, then you would not get any support_language entity
#    which means you don't get any tour_spot_info with the selected support_language
# 4. The enabled of tour_spot_info depends on the enabled of tour_spot
create table `tour_spot_info`
(
  `id`                        int(11)       not null auto_increment,
  `name`                      varchar(100)  not null,
  `enabled`                   tinyint(1)    not null,
  `description`               varchar(1000) not null,
  `tag_name`                  varchar(50)   not null,
  `always_open`               tinyint(1)    not null, -- just in case it is indexed
  `average_rate`              decimal(3, 1),          -- out of 10
  `number_of_rate`            int(11)       not null,
  `popular_score`             decimal(3, 1) not null,
  `number_of_wish_list_saved` int(11)       not null,
  `rep_image_path`            varchar(4096),
  `city_id`                   int(11)       not null, -- denormal
  `region_id`                 int(11),                -- denormalization
  `tour_spot_id`              int(11)       not null,
  `support_language_id`       int(11)       not null,
  `created_at`                timestamp     not null default current_timestamp,
  `updated_at`                timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  #     index `enabled_city_id_support_language_id_by_tour_spot_info` (`enabled`, `city_id`, `support_language_id`),
  constraint `fk_city_id_by_tour_spot_info` foreign key (`city_id`) references `city` (`id`),
  constraint `fk_region_id_by_tour_spot_info` foreign key (`region_id`) references `region` (`id`),
  constraint `fk_tour_spot_id_by_tour_spot_info` foreign key (`tour_spot_id`) references `tour_spot` (`id`),
  constraint `fk_support_language_id_by_tour_spot_info` foreign key (`support_language_id`) references `support_language` (`id`)
);



select *
from tour_spot_info;

create table `tour_spot_trading_hour`
(
  `id`                   int(11)   not null auto_increment,
  `open`                 time,
  `close`                time,
  `tour_spot_id`         int(11)   not null,
  `day_of_week_id`       int(11)   not null,
  `trading_hour_type_id` int(11)   not null,
  `created_at`           timestamp not null default current_timestamp,
  `updated_at`           timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  #     index `tour_spot_id_open_close` (`tour_spot_id`, `open`, `close`),
  constraint `fk_tour_spot_id_by_tour_spot_trading_hour` foreign key (`tour_spot_id`) references `tour_spot` (`id`),
  constraint `fk_day_of_week_id_by_tour_spot_trading_hour` foreign key (`day_of_week_id`) references `day_of_week` (`id`),
  constraint `fk_trading_hour_type_id_by_trading_hour` foreign key (`trading_hour_type_id`) references `trading_hour_type` (`id`)
);



# 1. the modification of theme enabled column will affect city_theme enabled column
# 2. pass theme_id and support_language_id and get the result from cached data on the method of theme_info service
create table `city_theme_rel`
(
  `id`             int(11)    not null auto_increment,
  `enabled`        tinyint(1) not null,
  `num_of_all_tag` int(11)    not null,
  `city_id`        int(11)    not null,
  `theme_id`       int(11)    not null,

  primary key (`id`),
  constraint `fk_city_by_city_theme_rel` foreign key (`city_id`) references `city` (`id`),
  constraint `fk_theme_id_by_city_theme_rel` foreign key (`theme_id`) references `theme` (`id`)
);

# 1. when you add or delete tour_spot, you should plus or minus num_of_tag of corresponding tag to tags belonging
#    to the tour_spot
# 2. If num_of_tag reaches to 0, then remove or set enabled to false
# 3. Setting tag enabled to true or false will affect the enabled of the entries in this table
# 4. filter option will only contain tags belonging to tour_spot not activity
create table `city_theme_tag_rel`
(
  `id`                int(11)    not null auto_increment,
  `enabled`           tinyint(1) not null,
  `num_of_tag`        int(11)    not null,
  `city_theme_rel_id` int(11)    not null,
  `tag_id`            int(11)    not null,

  primary key (`id`),
  constraint `fk_city_theme_id_by_city_tag_rel` foreign key (`city_theme_rel_id`) references `city_theme_rel` (`id`),
  constraint `fk_tag_id_by_city_tag_rel` foreign key (`tag_id`) references `tag` (`id`)
);

create table `region_theme_rel`
(
  `id`             int(11)    not null auto_increment,
  `enabled`        tinyint(1) not null,
  `num_of_all_tag` int(11)    not null,
  `region_id`      int(11)    not null,
  `theme_id`       int(11)    not null,

  primary key (`id`),
  constraint `fk_region_id_by_region_theme_rel` foreign key (`region_id`) references `region` (`id`),
  constraint `fk_theme_id_by_region_theme_rel` foreign key (`theme_id`) references `theme` (`id`)
);


create table `region_theme_tag_rel`
(
  `id`                  int(11)    not null auto_increment,
  `enabled`             tinyint(1) not null,
  `num_of_tag`          int(11)    not null,
  `region_theme_rel_id` int(11)    not null,
  `tag_id`              int(11)    not null,
  `region_id`           int(11)    not null,

  primary key (`id`),
  constraint `fk_region_theme_rel_id_by_region_theme_tag_rel` foreign key (`region_theme_rel_id`) references `region_theme_rel` (`id`),
  constraint `fk_tag_id_by_region_theme_tag_rel` foreign key (`tag_id`) references `tag` (`id`)

);



create table `tour_spot_description`
(
  `id`           int(11)       not null auto_increment,
  `name`         varchar(100)  not null, -- subheading
  `enabled`      int(11)       not null,
  `order`        int(11)       not null,
  `popup`        tinyint(1)    not null,
  `description`  varchar(1000) not null,
  `tour_spot_id` int(11)       not null,
  `created_at`   timestamp     not null default current_timestamp,
  `updated_at`   timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_id_by_tour_spot_description` foreign key (`tour_spot_id`) references `tour_spot` (`id`)
);



# 1. description image will be queried independently and then bulk download from AWS
# 2. match description and images will process on client side
create table `tour_spot_description_info`
(
  `id`                       int(11)       not null auto_increment,
  `name`                     varchar(100)  not null,
  `enabled`                  tinyint(1)    not null, -- denormal
  `order`                    int(11)       not null, -- denormal
  `popup`                    tinyint(1)    not null, -- denormal
  `description`              varchar(1000) not null,
  `tour_spot_info_id`        int(11)       not null,
  `tour_spot_description_id` int(11)       not null,
  `support_language_id`      int(11)       not null,
  `created_at`               timestamp     not null default current_timestamp,
  `updated_at`               timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  #   index `tour_spot_id_enabled_support_language_id` (`tour_spot_id`, `enabled`, `support_language_id`),
  constraint `fk_tour_spot_info_id_by_tour_spot_description_info` foreign key (`tour_spot_info_id`) references `tour_spot_info` (`id`),
  constraint `fk_tour_spot_description_id_by_tour_spot_description_info` foreign key (`tour_spot_description_id`) references `tour_spot_description` (`id`),
  constraint `fk_support_language_id_by_tour_spot_description_info` foreign key (`support_language_id`) references `support_language` (`id`)
);


# 1. child, adult, student, disabled, local, group, pensioner,
create table `ticket_type`
(
  `id`                  int(11)     not null auto_increment,
  `name`                varchar(50) not null,
  `enabled`             tinyint(11) not null,
  `rep_image_path`      varchar(4096),
  `rep_image_file_type` varchar(10),
  `created_at`          timestamp   not null default current_timestamp,
  `updated_at`          timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

create table `ticket_type_info`
(
  `id`                  int(11)      not null auto_increment,
  `name`                varchar(100) not null,
  `enabled`             tinyint(1)   not null,
  `rep_image_path`      varchar(4096),
  `rep_image_file_type` varchar(10),
  `ticket_type_id`      int(11)      not null,
  `support_language_id` int(11)      not null,
  `created_at`          timestamp    not null default current_timestamp,
  `updated_at`          timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_ticket_type_id_by_ticket_type_info` foreign key (`ticket_type_id`) references `ticket_type` (`id`),
  constraint `fk_support_language_id_by_ticket_type_info` foreign key (`support_language_id`) references `support_language` (`id`)
);



create table `tour_spot_ticket`
(
  `id`             int(11)        not null auto_increment,
  `name`           varchar(100)   not null,
  `enabled`        tinyint(1)     not null,
  `description`    varchar(500)   not null,
  `order`          int            not null,
  `rep_price`      decimal(10, 2) not null, -- by default price for adult is representative price
  `rep_image_path` varchar(4096),
  `tour_spot_id`   int(11)        not null,
  `created_at`     timestamp      not null default current_timestamp,
  `updated_at`     timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_id_by_tour_spot_ticket` foreign key (`tour_spot_id`) references `tour_spot` (`id`)
);



create table `tour_spot_ticket_info`
(
  `id`                  int(11)        not null auto_increment,
  `name`                varchar(100)   not null,
  `enabled`             tinyint(1)     not null, -- denormalization
  `description`         varchar(500)   not null,
  `order`               int            not null,
  `rep_price`           decimal(10, 2) not null, -- by default price for adult is representative price
  `rep_image_path`      varchar(4096),
  `tour_spot_info_id`   int(11)        not null,
  `tour_spot_ticket_id` int(11)        not null,
  `support_language_id` int(11)        not null,
  `created_at`          timestamp      not null default current_timestamp,
  `updated_at`          timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_ticket_id_by_tour_spot_ticket_info` foreign key (`tour_spot_ticket_id`) references `tour_spot_ticket` (`id`),
  constraint `fk_tour_spot_info_id_by_tour_spot_ticket_info` foreign key (`tour_spot_info_id`) references `tour_spot_info` (`id`),
  constraint `fk_support_language_id_by_tour_spot_ticket_info` foreign key (`support_language_id`) references `support_language` (`id`)
);



create table `tour_spot_ticket_price`
(
  `id`                  int(11)        not null auto_increment,
  `price`               decimal(10, 2) not null,
  `rep_price`           tinyint(1)     not null,
  `ticket_type_id`      int(11)        not null,
  `tour_spot_ticket_id` int(11)        not null,
  `created_at`          timestamp      not null default current_timestamp,
  `updated_at`          timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_ticket_type_id_by_tour_spot_ticket_price` foreign key (`ticket_type_id`) references `ticket_type` (`id`),
  constraint `fk_tour_spot_ticket_id_by_tour_spot_ticket_price` foreign key (`tour_spot_ticket_id`) references `tour_spot_ticket` (`id`)

);


create table `tour_spot_ticket_description`
(
  `id`                  int(11)       not null auto_increment,
  `name`                varchar(100)  not null, -- subheading
  `enabled`             int(11)       not null,
  `order`               int(11)       not null,
  `popup`               tinyint(1)    not null,
  `description`         varchar(1000) not null,
  `tour_spot_ticket_id` int(11)       not null,
  `created_at`          timestamp     not null default current_timestamp,
  `updated_at`          timestamp     not null default current_timestamp on update current_timestamp,


  primary key (`id`),
  constraint `fk_tour_spot_ticket_id_by_tour_spot_ticket_description` foreign key (`tour_spot_ticket_id`) references `tour_spot_ticket` (`id`)
);


create table `tour_spot_ticket_description_info`
(
  `id`                              int(11)       not null auto_increment,
  `name`                            varchar(100)  not null, -- subheading
  `enabled`                         int(11)       not null,
  `order`                           int(11)       not null,
  `popup`                           tinyint       not null,
  `description`                     varchar(1000) not null,
  `tour_spot_ticket_info_id`        int(11)       not null,
  `tour_spot_ticket_description_id` int(11)       not null,
  `support_language_id`             int(11)       not null,
  `created_at`                      timestamp     not null default current_timestamp,
  `updated_at`                      timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_ticket_description_id` foreign key (`tour_spot_ticket_description_id`) references `tour_spot_ticket_description` (`id`),
  constraint `fk_support_language_id` foreign key (`support_language_id`) references `support_language` (`id`),
  constraint `fk_tour_spot_ticket_info_id` foreign key (`tour_spot_ticket_info_id`) references `tour_spot_ticket_info` (`id`)
);



create table `activity`
(
  `id`                        int(11)       not null auto_increment,
  `name`                      varchar(100)  not null,
  `enabled`                   tinyint(1)    not null,
  `description`               varchar(1000) not null,
  `average_rate`              decimal(3, 1) not null, -- out of 10
  `number_of_rate`            int(11)       not null,
  `number_of_wish_list_saved` int(11)       not null,
  `popular_score`             int(11)       not null,
  `rep_image_path`            varchar(4096),
  `tour_spot_id`              int(11)       not null,
  `tag_id`                    int(11)       not null,
  `created_at`                timestamp     not null default current_timestamp,
  `updated_at`                timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_id_by_activity` foreign key (`tour_spot_id`) references `tour_spot` (`id`),
  constraint `fk_tag_id_by_activity` foreign key (`tag_id`) references `tag` (`id`)
);


# 1. this table will have tour_spot as dummy activity with tag_name empty "" per support_language_id. That's why the activity_id is nullable
# 2. tour_spot_info will left join activity_info and then where tag_id in (1,2,3,....)
# 3. order by average_rate, popular_score just in case there is no average_rate for activities selected

create table `activity_info`
(
  `id`                        int(11)       not null auto_increment,
  `name`                      varchar(100)  not null,
  `enabled`                   tinyint(1)    not null,
  `description`               varchar(1000) not null,
  `average_rate`              decimal(3, 1) not null, -- out of 10
  `number_of_rate`            int(11)       not null,
  `number_of_wish_list_saved` int(11)       not null,
  `popular_score`             int(11)       not null,
  `tag_name`                  varchar(50)   not null,
  `rep_image_path`            varchar(4096),
  `tour_spot_info_id`         int(11)       not null,
  `activity_id`               int(11)       not null,
  `tag_info_id`               int(11)       not null,
  `support_language_id`       int(11)       not null,
  `created_at`                timestamp     not null default current_timestamp,
  `updated_at`                timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  #     index `` (tour_spot_info_id, tag_id) for searching by filters of tags
  constraint `fk_tour_spot_info_id_by_activity_info` foreign key (`tour_spot_info_id`) references `tour_spot_info` (`id`),
  constraint `fk_activity_id_by_activity_info` foreign key (`activity_id`) references `activity` (`id`),
  constraint `fk_tag_info_id_by_activity_info` foreign key (`tag_info_id`) references `tag_info` (`id`),
  constraint `fk_support_language_id_by_activity_info` foreign key (`support_language_id`) references `support_language` (`id`)
);



create table `activity_description`
(
  `id`          int(11)       not null auto_increment,
  `name`        varchar(100)  not null,
  `enabled`     int(11)       not null,
  `order`       int(11)       not null,
  `description` varchar(1000) not null,
  `activity_id` int(11)       not null,
  `created_at`  timestamp     not null default current_timestamp,
  `updated_at`  timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_id_by_activity_description` foreign key (`activity_id`) references `activity` (`id`)
);


create table `activity_description_info`
(
  `id`                      int(11)       not null auto_increment,
  `name`                    varchar(100)  not null,
  `enabled`                 tinyint(1)    not null,
  `order`                   int(11)       not null, -- denormalization
  `description`             varchar(1000) not null,
  `activity_info_id`        int(11)       not null,
  `activity_description_id` int(11)       not null,
  `support_language_id`     int(11)       not null,
  `created_at`              timestamp     not null default current_timestamp,
  `updated_at`              timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_info_id_by_activity_description_info` foreign key (`activity_info_id`) references `activity_info` (`id`),
  constraint `fk_activity_description_id_by_activity_description_info` foreign key (`activity_description_id`) references `activity_description` (`id`),
  constraint `fk_support_language_id_by_activity_description_info` foreign key (`support_language_id`) references `support_language` (`id`)
);



create table `activity_ticket`
(
  `id`             int(11)        not null auto_increment,
  `name`           varchar(100)   not null,
  `enabled`        tinyint(1)     not null,
  `description`    varchar(500)   not null,
  `order`          int            not null,
  `rep_price`      decimal(10, 2) not null, -- by default price for adult is representative price
  `rep_image_path` varchar(4096),
  `activity_id`    int(11)        not null,
  `created_at`     timestamp      not null default current_timestamp,
  `updated_at`     timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_id` foreign key (`activity_id`) references `activity` (`id`)
);



create table `activity_ticket_info`
(
  `id`                  int(11)        not null auto_increment,
  `name`                varchar(100)   not null,
  `enabled`             tinyint(1)     not null, -- denormalization
  `description`         varchar(500)   not null,
  `order`               int            not null,
  `rep_price`           decimal(10, 2) not null, -- by default price for adult is representative price
  `rep_image_path`      varchar(4096),
  `activity_info_id`    int(11)        not null,
  `activity_ticket_id`  int(11)        not null,
  `support_language_id` int(11)        not null,
  `created_at`          timestamp      not null default current_timestamp,
  `updated_at`          timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_ticket_id_by_activity_ticket_info` foreign key (`activity_ticket_id`) references `activity_ticket` (`id`),
  constraint `fk_activity_info_id_by_activity_ticket_info` foreign key (`activity_info_id`) references `activity_info` (`id`),
  constraint `fk_support_language_id_by_activity_ticket_info` foreign key (`support_language_id`) references `support_language` (`id`)
);


create table `activity_ticket_price`
(
  `id`                 int(11)        not null auto_increment,
  `price`              decimal(10, 2) not null,
  `rep_price`          tinyint(1)     not null,
  `ticket_type_id`     int(11)        not null,
  `activity_ticket_id` int(11)        not null,
  `created_at`         timestamp      not null default current_timestamp,
  `updated_at`         timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_ticket_type_id_by_activity_ticket_price` foreign key (`ticket_type_id`) references `ticket_type` (`id`),
  constraint `fk_activity_ticket_id_by_activity_ticket_price` foreign key (`activity_ticket_id`) references `activity_ticket` (`id`)

);


create table `activity_ticket_description`
(
  `id`                 int(11)       not null auto_increment,
  `name`               varchar(100)  not null, -- subheading
  `enabled`            int(11)       not null,
  `order`              int(11)       not null,
  `popup`              tinyint(1)    not null,
  `description`        varchar(1000) not null,
  `activity_ticket_id` int(11)       not null,
  `created_at`         timestamp     not null default current_timestamp,
  `updated_at`         timestamp     not null default current_timestamp on update current_timestamp,


  primary key (`id`),
  constraint `fk_activity_ticket_id_by_activity_ticket_description` foreign key (`activity_ticket_id`) references `activity_ticket` (`id`)
);


create table `activity_ticket_description_info`
(
  `id`                             int(11)       not null auto_increment,
  `name`                           varchar(100)  not null, -- subheading
  `enabled`                        int(11)       not null,
  `order`                          int(11)       not null,
  `popup`                          tinyint       not null,
  `description`                    varchar(1000) not null,
  `activity_ticket_info_id`        int(11)       not null,
  `activity_ticket_description_id` int(11)       not null,
  `support_language_id`            int(11)       not null,
  `created_at`                     timestamp     not null default current_timestamp,
  `updated_at`                     timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_ticket_description_id_by_act_ticket_des_info` foreign key (`activity_ticket_description_id`) references `activity_ticket_description` (`id`),
  constraint `fk_support_language_id_by_activity_ticket_description_info` foreign key (`support_language_id`) references `support_language` (`id`),
  constraint `fk_activity_ticket_info_id_by_activity_ticket_description_info` foreign key (`activity_ticket_info_id`) references `activity_ticket_info` (`id`)
);



create table admin
(
  `id`         int(11)      not null auto_increment,
  `username`   varchar(254) not null unique, -- email
  `password`   binary(60)   not null,
  `enabled`    tinyint(1)   not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);



insert into `admin`
values (null, 'c0go233@gmail.com',
        '$2b$10$oPDgDHWTkPhJjCrL6Vw9MedJe/QXUtuO31TI8IGDKRFE.DfqY0XRS',
        1, current_timestamp(), current_timestamp());


insert into `admin`
values (null, 'cnc332@naver.com',
        '$2b$10$oPDgDHWTkPhJjCrL6Vw9MedJe/QXUtuO31TI8IGDKRFE.DfqY0XRS',
        1, current_timestamp(), current_timestamp());

insert into `admin`
values (null, 'Jungbaer@naver.com',
        '$2b$10$oPDgDHWTkPhJjCrL6Vw9MedJe/QXUtuO31TI8IGDKRFE.DfqY0XRS',
        1, current_timestamp(), current_timestamp());



create table `authority`
(
  `id`         int         not null auto_increment,
  `name`       varchar(50) not null,
  `enabled`    tinyint(1)  not null,
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

insert into authority
values (null, 'user', 1, current_timestamp, current_timestamp);
insert into authority
values (null, 'admin', 1, current_timestamp, current_timestamp);
insert into authority
values (null, 'root', 1, current_timestamp, current_timestamp);


create table `admin_authority_rel`
(
  `admin_id`     int not null,
  `authority_id` int not null,

  primary key (`admin_id`, `authority_id`),
  constraint `fk_admin_id_by_admin_authority_rel` foreign key (`admin_id`) references `admin` (`id`),
  constraint `fk_authority_id_by_admin_authority_rel` foreign key (`authority_id`) references `authority` (`id`)
);

insert into admin_authority_rel
values (1, 3);
insert into admin_authority_rel
values (1, 2);
insert into admin_authority_rel
values (1, 1);

insert into admin_authority_rel
values (2, 2);
insert into admin_authority_rel
values (2, 1);

insert into admin_authority_rel
values (3, 2);
insert into admin_authority_rel
values (3, 1);


# TODO: 07/05/2020 exceute

# 1. photo_zone has its own ui even if it is tour_spot
# 2. when click photo_zone box, it will direct user to the photo_zone page then it will have "go to this tour_spot" in the page if it is both tour_spot and photo_zone
# 3. create table for a map for the photo_zone
create table `photo_zone`
(
  `id`                  int(11)       not null auto_increment,
  `enabled`             tinyint(1),
  `name`                varchar(100)  not null,
  `description`         varchar(1000) not null,
  `order`               int           not null,
  `latitude`            decimal(18, 12),
  `longitude`           decimal(18, 12),
  `address`             varchar(1000),
  `rep_image_path`      varchar(4096),
  `tour_spot_id`        int(11), -- nullable because there are photo_zones which are not tour_spots
  `activity_id`         int(11),
  `parent_tour_spot_id` int(11)       not null,
  `created_at`          timestamp     not null default current_timestamp,
  `updated_at`          timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_id_by_photo_zone` foreign key (`tour_spot_id`) references `tour_spot` (`id`),
  constraint `fk_activity_id_by_photo_zone` foreign key (`activity_id`) references `activity` (`id`),
  constraint `fk_parent_tour_spot_id_by_photo_zone` foreign key (`parent_tour_spot_id`) references `tour_spot` (`id`)
);


create table `photo_zone_info`
(
  `id`                       int(11)       not null auto_increment,
  `enabled`                  tinyint(1),
  `name`                     varchar(100)  not null,
  `description`              varchar(1000) not null,
  `order`                    int           not null,
  `latitude`                 decimal(18, 12),
  `longitude`                decimal(18, 12),
  `address`                  varchar(1000),
  `rep_image_path`           varchar(4096),
  `photo_zone_id`            int(11)       not null,
  `parent_tour_spot_info_id` int           not null,
  `tour_spot_info_id`        int(11),
  `activity_info_id`         int(11),
  `support_language_id`      int(11)       not null,
  `created_at`               timestamp     not null default current_timestamp,
  `updated_at`               timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_photo_zone_id_by_photo_zone_info` foreign key (`photo_zone_id`) references `photo_zone` (`id`),
  constraint `fk_parent_tour_spot_info_id_by_photo_zone_info` foreign key (`parent_tour_spot_info_id`) references `tour_spot_info` (`id`),
  constraint `fk_tour_spot_info_id_by_photo_zone_info` foreign key (`tour_spot_info_id`) references `tour_spot_info` (`id`),
  constraint `fk_activity_info_id_by_photo_zone_info` foreign key (`activity_info_id`) references `activity_info` (`id`),
  constraint `fk_support_language_id_by_photo_zone_info` foreign key (`support_language_id`) references `support_language` (`id`)
);


create table `city_image`
(
  `id`         int(11)       not null auto_increment,
  `name`       varchar(255)  not null,
  `enabled`    tinyint(1)    not null,
  `order`      int           not null,
  `file_type`  varchar(10)   not null,
  `bucket_key` varchar(1000) not null,
  `rep_image`  tinyint(1)    not null,
  #   `width`      int(10)       not null,
  #   `height`     int(10)       not null,
  `city_id`    int(11)       not null,
  `created_at` timestamp     not null default current_timestamp,
  `updated_at` timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_city_id_by_city_image` foreign key (`city_id`) references `city` (`id`)
);


# TODO: execute 11/05/2020

create table `region_image`
(
  `id`         int(11)       not null auto_increment,
  `name`       varchar(255)  not null,
  `enabled`    tinyint(1)    not null,
  `order`      int           not null,
  `file_type`  varchar(10)   not null,
  `bucket_key` varchar(1000) not null,
  `rep_image`  tinyint(1)    not null,
  #     `width`      int(10)       not null,
  #     `height`     int(10)       not null,
  `region_id`  int(11)       not null,
  `created_at` timestamp     not null default current_timestamp,
  `updated_at` timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_region_id_by_region_image` foreign key (`region_id`) references `region` (`id`)
);

create table `tour_spot_image`
(
  `id`           int(11)       not null auto_increment,
  `name`         varchar(255)  not null,
  `enabled`      tinyint(1)    not null,
  `order`        int           not null,
  `file_type`    varchar(10)   not null,
  `bucket_key`   varchar(1000) not null,
  `rep_image`    tinyint(1)    not null,
  #     `width`        int(10)       not null,
  #     `height`       int(10)       not null,
  `tour_spot_id` int(11)       not null,
  #   `season_id`                int(11)       not null,
  `created_at`   timestamp     not null default current_timestamp,
  `updated_at`   timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_id_by_tour_spot_image` foreign key (`tour_spot_id`) references `tour_spot` (`id`)
  #   constraint `fk_season_id` foreign key (`season_id`) references `season` (`id`)
);

# 1. when converting to dto, put order of tour_spot_description so that it can be easily mapped on client side
# 2. select * from this table where tour_spot_id = :tour_spot_id so no need to left join or something
# 3. order of this table is the order of picture on description
create table `tour_spot_description_image`
(
  `id`                       int(11)       not null auto_increment,
  `name`                     varchar(255)  not null,
  `enabled`                  tinyint(1)    not null,
  `order`                    int           not null,
  `file_type`                varchar(10)   not null,
  `bucket_key`               varchar(1000) not null,
  #     `width`                    int(10)       not null,
  #     `height`                   int(10)       not null,
  `tour_spot_id`             int(11)       not null,
  `tour_spot_description_id` int(11)       not null,
  #   `season_id`                int(11)       not null,
  `created_at`               timestamp     not null default current_timestamp,
  `updated_at`               timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_id_by_tour_spot_description_image` foreign key (`tour_spot_id`) references `tour_spot` (`id`),
  constraint `fk_tour_spot_description_id_by_tour_spot_description_image` foreign key (`tour_spot_description_id`) references `tour_spot_description` (`id`)
);



create table `tour_spot_ticket_image`
(
  `id`                  int(11)       not null auto_increment,
  `name`                varchar(255)  not null,
  `enabled`             tinyint(1)    not null,
  `order`               int           not null,
  `file_type`           varchar(10)   not null,
  `bucket_key`          varchar(1000) not null,
  `rep_image`           tinyint(1)    not null,
  #     `width`               int(10)       not null,
  #     `height`              int(10)       not null,
  `tour_spot_ticket_id` int(11)       not null,
  #   `season_id`                int(11)       not null,
  `created_at`          timestamp     not null default current_timestamp,
  `updated_at`          timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_ticket_id_by_tour_spot_ticket_image` foreign key (`tour_spot_ticket_id`) references `tour_spot_ticket` (`id`)
);

create table `tour_spot_ticket_description_image`
(
  `id`                              int(11)       not null auto_increment,
  `name`                            varchar(255)  not null,
  `enabled`                         tinyint(1)    not null,
  `order`                           int           not null,
  `file_type`                       varchar(10)   not null,
  `bucket_key`                      varchar(1000) not null,
  #     `width`                           int(10)       not null,
  #     `height`                          int(10)       not null,
  `tour_spot_ticket_id`             int(11)       not null,
  `tour_spot_ticket_description_id` int(11)       not null,
  #   `season_id`                int(11)       not null,
  `created_at`                      timestamp     not null default current_timestamp,
  `updated_at`                      timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_ticket_id_by_tour_spot_ticket_description_image` foreign key (`tour_spot_ticket_id`) references `tour_spot_ticket` (`id`),
  constraint `fk_tour_spot_ticket_description_id_by_tour_spot_ticket_desc_img` foreign key (`tour_spot_ticket_description_id`) references `tour_spot_ticket_description` (`id`)
);


-- season will be populated on application level when start so does not need denorlization?
-- tour_spot_image stores image of tour spot in different seasons, and in the list of tour_spot, it will display representative picture regardless of its season, but when detailed tour_spot page show current season's pictures
-- tour_spot_image stores all the images for this tour_spot even the ones that are used in description, activity, tickets
create table `activity_image`
(
  `id`          int(11)       not null auto_increment,
  `name`        varchar(255)  not null,
  `enabled`     tinyint(1)    not null,
  `order`       int           not null,
  `file_type`   varchar(10)   not null,
  `bucket_key`  varchar(1000) not null,
  `rep_image`   tinyint(1)    not null,
  #     `width`                           int(10)       not null,
  #     `height`                          int(10)       not null,
  `activity_id` int(11)       not null,
  `created_at`  timestamp     not null default current_timestamp,
  `updated_at`  timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_id_by_activity_image` foreign key (`activity_id`) references `activity` (`id`)
);

create table `activity_description_image`
(
  `id`                      int(11)       not null auto_increment,
  `name`                    varchar(255)  not null,
  `enabled`                 tinyint(1)    not null,
  `order`                   int           not null,
  `file_type`               varchar(10)   not null,
  `bucket_key`              varchar(1000) not null,
  #     `width`                    int(10)       not null,
  #     `height`                   int(10)       not null,
  `activity_id`             int(11)       not null,
  `activity_description_id` int(11)       not null,
  #   `season_id`                int(11)       not null,
  `created_at`              timestamp     not null default current_timestamp,
  `updated_at`              timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_id_by_activity_description_image` foreign key (`activity_id`) references `activity` (`id`),
  constraint `fk_activity_description_id_by_activity_description_image` foreign key (`activity_description_id`) references `activity_description` (`id`)
);


-- ticket also has pictures frames on the top so that it should load all the picture beloging to this ticket, that's why tour_spot_cticket_id is there
-- if picture is only used for ticket not in tour_spot, then tour_spot_image_id null and then create relatvePath by itself and only shared in this table not tour_spot_image
-- most of images used in ticket will refer to the images in tour_spot_image
create table `activity_ticket_image`
(
  `id`                 int(11)       not null auto_increment,
  `name`               varchar(255)  not null,
  `enabled`            tinyint(1)    not null,
  `order`              int           not null,
  `file_type`          varchar(10)   not null,
  `bucket_key`         varchar(1000) not null,
  `rep_image`          tinyint(1)    not null,
  #     `width`                           int(10)       not null,
  #     `height`                          int(10)       not null,
  `activity_ticket_id` int(11)       not null,
  `created_at`         timestamp     not null default current_timestamp,
  `updated_at`         timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_ticket_id_by_activity_ticket_image` foreign key (`activity_ticket_id`) references `activity_ticket` (`id`)
);



create table `activity_ticket_description_image`
(
  `id`                             int(11)       not null auto_increment,
  `name`                           varchar(255)  not null,
  `enabled`                        tinyint(1)    not null,
  `order`                          int           not null,
  `file_type`                      varchar(10)   not null,
  `bucket_key`                     varchar(1000) not null,
  #     `width`                    int(10)       not null,
  #     `height`                   int(10)       not null,
  `activity_ticket_id`             int(11)       not null,
  `activity_ticket_description_id` int(11)       not null,
  #   `season_id`                int(11)       not null,
  `created_at`                     timestamp     not null default current_timestamp,
  `updated_at`                     timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_activity_ticket_id_by_activity_ticket_description_image` foreign key (`activity_ticket_id`) references `activity_ticket` (`id`),
  constraint `fk_activity_ticket_description_id_by_activity_ticket_desc_image` foreign key (`activity_ticket_description_id`) references `activity_ticket_description` (`id`)
);


-- every pic is the ones taken in the photo_zone
create table `photo_zone_image`
(
  `id`            int(11)       not null auto_increment,
  `name`          varchar(255)  not null,
  `enabled`       tinyint(1)    not null,
  `order`         int           not null,
  `file_type`     varchar(10)   not null,
  `bucket_key`    varchar(1000) not null,
  `rep_image`     tinyint(1)    not null,
  #     `width`                    int(10)       not null,
  #     `height`                   int(10)       not null,
  `photo_zone_id` int(11)       not null,
  `created_at`    timestamp     not null default current_timestamp,
  `updated_at`    timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_photo_zone_id` foreign key (`photo_zone_id`) references `photo_zone` (`id`)
);


select *
from photo_zone;

drop table tour_spot_image;
drop table tour_spot_description_image;
drop table tour_spot_ticket_image;
drop table tour_spot_ticket_description_image;
drop table activity_image;
drop table activity_description_image;
drop table activity_ticket_image;
drop table activity_ticket_description_image;
drop table photo_zone_image;


select *
from tour_spot_description_image;


-- ############################################DONE########################################################################################################################
-- ############################################DONE########################################################################################################################
-- ############################################DONE########################################################################################################################
-- ############################################DONE########################################################################################################################
-- ############################################DONE########################################################################################################################
-- ############################################DONE########################################################################################################################
-- ############################################DONE########################################################################################################################
-- ############################################DONE########################################################################################################################


-- tour_spot_image stores image of tour spot in different seasons, and in the list of tour_spot, it will display representative picture regardless of its season, but when detailed tour_spot page show current season's pictures
-- tour_spot_image stores images used in the description of this tour_spot
-- when you touch the representing image on the top then it will show images used in the descriptions of this tour_spot
-- There is no reason to make another tour_spot_representative_image table because of how btree stores indexes
-- For list of tour_spots then join tour_spot_image and where device_type_id = @device_type_id and put relative path to the object of tour_spot_info by using "as"
-- For detail page of tour_spot then execute another query to get all images with enabled, device_type_id = @device_type_id and sort and put images to tour_spot_description on user's mobile or web
-- tour_spot_image class will have season name variables and query will have as season name with season table joined with support_language considered


# TODO: have not implemented


# TODO: have not implemented


# 1. login type include email and password login, Kakao, Facebook, Google logins
create table `login_type`
(
  `id`         int(11)     not null auto_increment,
  `name`       varchar(50) not null,
  `enabled`    tinyint(1)  not null,
  `order`      int         not null, -- to choose the order of display for login or signup page like signup with facebook
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

insert into `login_type`
values (null, '이메일로 로그인 하기', 1, 0, current_timestamp(), current_timestamp());



create table `currency`
(
  `id`         int(11)      not null auto_increment,
  `code`       varchar(100) not null,
  `name`       varchar(50)  not null,
  `symbol`     varchar(10)  not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

insert into `currency`
values (null, 'KRW', '원', '₩', current_timestamp(), current_timestamp());
insert into `currency`
values (null, 'USD', '미국달러', '$', current_timestamp(), current_timestamp());

create table `persistent_logins`
(
  `username`  varchar(254) not null,
  `series`    varchar(64),
  `token`     varchar(64)  not null,
  `last_used` timestamp    not null,

  primary key (`series`),
  constraint `fk_username` foreign key (`username`) references `user` (`username`)
);


# 1. Save currency_id, support_language_id in cookies so that you don't have to hit database
create table `user`
(
  `id`                      int(11)      not null auto_increment,
  `username`                varchar(254) not null unique, -- email
  `password`                binary(60)   not null,
  `enabled`                 tinyint(1)   not null,
  `suspended_by`            timestamp    not null default current_timestamp,
  `profile_image_path`      varchar(4096),
  `profile_image_file_type` varchar(10),
  `login_type_id`           int(11)      not null,
  `support_language_id`     int(11)      not null,
  `currency_id`             int(11)      not null,
  `created_at`              timestamp    not null default current_timestamp,
  `updated_at`              timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_login_type_id_by_user` foreign key (`login_type_id`) references `login_type` (`id`),
  constraint `fk_support_language_id_by_user` foreign key (`support_language_id`) references `support_language` (`id`),
  constraint `fk_currency_id_by_user` foreign key (`currency_id`) references `currency` (`id`)

);


-- every pic is the ones taken in the photo_zone
-- create table `photo_zone_map_image` (
-- 	`id` binary(16) not null,
--     `photo_zone_id` int(11) not null,
--     `relative_path` varchar(4096) not null,
--     `file_type` varchar(10) not null,
--     `file_name` varchar(255) not null,
--     `width` int(10) not null,
--     `height` int(10) not null,
--     `enabled` tinyint(1) not null,
--     `created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_photo_zone_id` foreign key(`photo_zone_id`) references `photo_zone` (`id`)
-- );

-- add user profiel image relative path for denoaml
create table `user_tour_spot_review`
(
  `id`           int(11)       not null auto_increment,
  `user_id`      int(11)       not null,
  `tour_spot_id` int(11)       not null,
  `enabled`      tinyint(1)    not null,
  `rate`         int(1)        not null,
  `comment`      varchar(1000) not null,
  `created_at`   timestamp     not null default current_timestamp,
  `updated_at`   timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`),
  constraint `fk_tour_spot_id` foreign key (`tour_spot_id`) references `tour_spot` (`id`)
);

-- tour_spot_review images do not belong to the pics at the top of the tour_spot page. but if you want to include some of the tour_spot_review images to the top compliation of tour_spot_images then you just create entry in the tour_spot_image
create table `user_tour_spot_review_image`
(
  `id`                       binary(16)    not null,
  `user_tour_spot_review_id` int(11)       not null,
  `relative_path`            varchar(4096) not null,
  `file_type`                varchar(10)   not null,
  `file_name`                varchar(255)  not null,
  `width`                    int(10)       not null,
  `height`                   int(10)       not null,
  `enabled`                  tinyint(1)    not null,
  `created_at`               timestamp     not null default current_timestamp,
  `updated_at`               timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_tour_spot_review_id` foreign key (`user_tour_spot_review_id`) references `user_tour_spot_review` (`id`)
);

create table `user_tour_spot_review_point_history`
(
  `id`                       int(11)   not null auto_increment,
  `user_id`                  int(11)   not null,
  `point`                    int(11)   not null,
  `user_tour_spot_review_id` int(11)   not null,
  `created_at`               timestamp not null default current_timestamp,
  `updated_at`               timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_tour_spot_review_id` foreign key (`user_tour_spot_review_id`) references `user_tour_spot_review` (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`)
);

create table `report_user_tour_spot_review`
(
  `id`                       int(11)       not null auto_increment,
  `report_reason_id`         int(11)       not null,
  `reporter_id`              int(11)       not null,
  `reporter_email`           varchar(254), -- optional
  `user_tour_spot_review_id` int(11)       not null,
  `description`              varchar(1000) not null,
  `created_at`               timestamp     not null default current_timestamp,
  `updated_at`               timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_tour_spot_review_id` foreign key (`user_tour_spot_review_id`) references `user_tour_spot_review` (`id`),
  constraint `fk_reporter_id` foreign key (`reporter_id`) references `user` (`id`),
  constraint `fk_report_reason_id` foreign key (`report_reason_id`) references `report_reason` (`id`)

);

create table `report_user_tour_spot_review_result`
(
  `id`                              int(11)        not null auto_increment,
  `report_user_tour_spot_review_id` int(11)        not null,
  `report_result_type_id`           int(11)        not null,
  `admin_id`                        int(11)        not null,
  `description`                     varchar(21844) not null,

  primary key (`id`),
  constraint `fk_report_user_tour_spot_review_id` foreign key (`report_user_tour_spot_review_id`) references `report_user_tour_spot_review` (`id`),
  constraint `fk_report_result_type_id` foreign key (`report_result_type_id`) references `report_result_type` (`id`),
  constraint `fk_admin_id` foreign key (`admin_id`) references `user` (`id`)
);



create table `wish_list`
(
  `id`                int(11)   not null auto_increment,
  `user_id`           int(11)   not null,
  `tour_spot_info_id` int(11)   not null,
  `created_at`        timestamp not null default current_timestamp,
  `updated_at`        timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_tour_spot_info_id` foreign key (`tour_spot_info_id`) references `tour_spot_info` (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`)
);

create table `itinerary`
(
  `id`         int(11)      not null auto_increment,
  `name`       varchar(100) not null,
  `user_id`    int(11)      not null,
  `start_date` timestamp    not null,
  `end_date`   timestamp    not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`)
);

-- order by updated_at?
create table `itinerary_tour_spot`
(
  `id`                int(11)   not null auto_increment,
  `itinerary_id`      int(11)   not null,
  `tour_spot_info_id` int(11), -- if it is just note then it is null
  `selected_date`     timestamp,
  `note`              varchar(1000),
  `created_at`        timestamp not null default current_timestamp,
  `updated_at`        timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_itinerary_id` foreign key (`itinerary_id`) references `itinerary` (`id`),
  constraint `fk_tour_spot_info_id` foreign key (`tour_spot_info_id`) references `tour_spot_info` (`id`)
);



create table `itinerary_city`
(
  `id`           int(11)   not null auto_increment,
  `itinerary_id` int(11)   not null,
  `city_info_id` int(11)   not null,
  `created_at`   timestamp not null default current_timestamp,
  `updated_at`   timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_itinerary_id` foreign key (`itinerary_id`) references `itinerary` (`id`),
  constraint `fk_city_info_id` foreign key (`city_info_id`) references `city_info` (`id`)
);


-- For easy management, when you add new support_language, you should add descriptions of the new support_language to tables added to this table. E.g. tables with the column of support_language_id
create table `language_based_table`
(
  `id`         int(11)     not null auto_increment,
  `name`       varchar(50) not null,
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);



create table `login_type_info`
(
  `id`                  int(11)     not null auto_increment,
  `name`                varchar(50) not null,
  `enabled`             tinyint(1)  not null, -- denormal
  `order`               int(11)     not null, -- denormal
  `support_language_id` int(11)     not null,
  `login_type_id`       int(11)     not null,
  `created_at`          timestamp   not null default current_timestamp,
  `updated_at`          timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_support_language_id_by_login_type_info` foreign key (`support_language_id`) references `support_language` (`id`),
  constraint `fk_login_type_id_by_login_type_info` foreign key (`login_type_id`) references `login_type` (`id`)
);

insert into `login_type_info`
values (null, "이메일로 로그인 하기", 1, 0, 1, 1, current_timestamp(), current_timestamp());
insert into `login_type_info`
values (null, "Login with my email", 1, 0, 1, 1, current_timestamp(), current_timestamp());

-- user < admin < master
create table `role`
(
  `id`         int(11)     not null auto_increment,
  `name`       varchar(50) not null,
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

insert into `role`
values (null, "user", current_timestamp(), current_timestamp());
insert into `role`
values (null, "admin", current_timestamp(), current_timestamp());
insert into `role`
values (null, "master", current_timestamp(), current_timestamp());


create table `permission`
(
  `id`         int(11)      not null auto_increment,
  `name`       varchar(200) not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

create table `role_permission_relationship`
(
  `id`            int(11)   not null auto_increment,
  `role_id`       int(11)   not null,
  `permission_id` int(11)   not null,
  `created_at`    timestamp not null default current_timestamp,
  `updated_at`    timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_role_id_by_role_permission_relationship` foreign key (`role_id`) references `role` (`id`),
  constraint `fk_permission_id_by_role_permission_relationship` foreign key (`permission_id`) references `permission` (`id`)
);


-- terms enabled affects all the entries in terms_info enabled
create table `terms`
(
  `id`         int(11)     not null auto_increment,
  `name`       varchar(50) not null,
  `enabled`    tinyint(1)  not null,
  `required`   tinyint(1)  not null,
  `order`      int(2)      not null,
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_terms_type_id` foreign key (`terms_type_id`) references `terms_type` (`id`)
);

-- if there is no terms for user support_langague, then deafult support_langage is korean
-- // top 1
-- // where support_langauge_id = :support_langauge_id
-- // group by terms_id
-- // order by effective_date_at
-- // change support_langage will send another request
-- // does not show previous terms and conditions but archvied in this table
-- update on effective_date so that spring can remove cached terms
create table `terms_info`
(
  `id`                  int(11)     not null auto_increment,
  `name`                varchar(50) not null,
  `terms_id`            int(11)     not null,
  `support_language_id` int(11)     not null,
  `enabled`             tinyint(1)  not null,
  `required`            tinyint(1)  not null,
  `order`               int(2)      not null,
  `description`         text        not null, -- to store html formatted text
  `effective_date_at`   timestamp   not null,
  `created_at`          timestamp   not null default current_timestamp,
  `updated_at`          timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_terms_id` foreign key (`terms_id`) references `terms` (`id`),
  constraint `fk_support_language_id` foreign key (`support_language_id`) references `support_language` (`id`)
);



-- suspend time is based on day. E.g. 1 Day, 2 Day, etc... so that permanent suspend will add 356000 Days 100 years
-- for easy management, suspend time will display in drop-down when admin suspend some users
-- suspend time table does not need order column because it can be ordered by `time`
-- This table could be used for suspend review
create table `suspend_time`
(
  `id`         int(11)     not null auto_increment,
  `name`       varchar(50) not null,
  `time`       int(11)     not null,
  `created_at` timestamp   not null default current_timestamp,
  `updated_at` timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);



--
-- create table `admin` (
-- )


-- 홍보용, 사업성, 욕설, 선정성, 불법,정보, 개인정보, 도배, 권리침해, 기타
create table `report_reason`
(
  `id`         int(11)    not null auto_increment,
  `enabled`    tinyint(1) not null,
  `order`      int(11)    not null,
  `for_admin`  tinyint(1) not null, -- for the reasons avaulable only for admin like suspecious activity like try to write with other users id
  `created_at` timestamp  not null default current_timestamp,
  `updated_at` timestamp  not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

-- cached table
create table `report_reason_info`
(
  `id`                  int(11)     not null auto_increment,
  `report_reason_id`    int(11)     not null,
  `support_language_id` int(11)     not null,
  `name`                varchar(50) not null,
  `enabled`             tinyint(1)  not null,                                                    -- denormal
  `order`               int(2)      not null,                                                    -- denormal
  `for_admin`           tinyint(1)  not null,                                                    -- denormal
  `created_at`          timestamp   not null default current_timestamp,
  `updated_at`          timestamp   not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  index `support_language_id_enabled_for_admin` (`support_language_id`, `enabled`, `for_admin`), -- fetch when user clicks report button and display these reasons based on support_language_id, enabled, for_admin
  constraint `fk_report_reason_id` foreign key (`report_reason_id`) references `report_reason` (`id`),
  constraint `fk_support_language_id` foreign key (`support_language_id`) references `support_language` (`id`)
);

-- suspend login, suspend review, discard, fake report and discard
-- it does not need info table because it is only for management
create table `report_result_type`
(
  `id`         int(11)      not null auto_increment,
  `name`       varchar(100) not null,
  `enabled`    tinyint(1)   not null,
  `order`      int(11)      not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

-- report_result_type_id is null meaning it has been recieved but not handled
create table `report_user`
(
  `id`               int(11)        not null auto_increment,
  `report_reason_id` int(11)        not null,
  `reporter_id`      int(11)        not null,
  `reporter_email`   varchar(254)   not null, -- auto-populate but user can change their email address
  `reported_user_id` int(11)        not null,
  `description`      varchar(21844) not null,
  `created_at`       timestamp      not null default current_timestamp,
  `updated_at`       timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_reporter_id` foreign key (`reporter_id`) references `user` (`id`),
  constraint `fk_reported_user_id` foreign key (`reported_user_id`) references `user` (`id`),
  constraint `fk_report_reason_id` foreign key (`report_reason_id`) references `report_reason` (`id`)
);

-- When user is reported first, it will create entry in report_user table then when admin does something then it will create entry in report_user_result table
create table `report_user_result`
(
  `id`                    int(11)        not null auto_increment,
  `report_user_id`        int(11)        not null,
  `report_result_type_id` int(11)        not null,
  `admin_id`              int(11)        not null,
  `description`           varchar(21844) not null,

  primary key (`id`),
  constraint `fk_report_user_id` foreign key (`report_user_id`) references `report_user` (`id`),
  constraint `fk_report_result_type_id` foreign key (`report_result_type_id`) references `report_result_type` (`id`),
  constraint `fk_admin_id` foreign key (`admin_id`) references `user` (`id`)
);

-- bucket4j will rate limit based on IP address
-- login_suspend_history is for suspicious acvitiy in the apple which is different from rate limiting like try to write with another user or something along those lines
-- When you add new entry with the same user id then the old entries will have enabled = false while the new entry will be enabled
-- Whenever you add new entries, it will update user suspend_by field, if cancel suspension then the suspension entry will be disabled and then suspend_by in user will be equal to now()
-- The workflow is as follows: admin or user must report users first then chose either login suspension, review suspension, or discard
-- Canceling suspension will happen in this table by making new entries with suspend_time_id equals to 0 which means 0 minutes, making suspend_tiem_by in user tbale equal to current time
create table `login_suspend_history`
(
  `id`              int(11)        not null auto_increment,
  `user_id`         int(11)        not null,
  `enabled`         tinyint(1)     not null,
  `suspend_time_id` int(11)        not null,                           -- does need denormalization because it is just for admin and management
  `report_user_id`  int(11)        not null,
  `admin_id`        int(11)        not null,
  `comment`         varchar(21844) not null,                           -- describe the reason for suspension or canceling suspension
  `created_at`      timestamp      not null default current_timestamp, -- create time works as suspension start time suspend_by = created_at + suspend_time
  `updated_at`      timestamp      not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`),
  constraint `fk_suspend_time_id` foreign key (`suspend_time_id`) references `suspend_time` (`id`),
  constraint `fk_admin_id` foreign key (`admin_id`) references `user` (`id`),
  constraint `fk_report_user_id` foreign key (`report_user_id`) references `report_user` (`id`)
);

-- Messages between users are not allowed
create table `inbox`
(
  `id`           int(11)      not null auto_increment,
  `recipient_id` int(11)      not null,
  `sender_id`    int(11)      not null,
  `sender_name`  varchar(100) not null, -- denormal
  `subject`      varchar(200) not null,
  `message`      text         not null,
  `read`         tinyint(1)   not null,
  `received_at`  timestamp    not null default current_timestamp,
  `updated_at`   timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_recipient_id` foreign key (`recipient_id`) references `user` (`id`),
  constraint `fk_sender_id` foreign key (`sender_id`) references `user` (`id`)
);

-- mobile, desktop, tablet, original for original image
create table `device_type`
(
  `id`         int(11)      not null auto_increment,
  `name`       varchar(100) not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

create table `image_frame_type`
(
  `id`         int(11)      not null auto_increment,
  `name`       varchar(100) not null,
  `created_at` timestamp    not null default current_timestamp,
  `updated_at` timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

-- store height and width of iamge according to device_type and image frame type
create table `image_size_for_frame_type`
(
  `id`                  int(11)      not null auto_increment,
  `name`                varchar(100) not null,
  `device_type_id`      int(11)      not null,
  `image_frame_type_id` int(11)      not null,
  `height`              int(11)      not null,
  `width`               int(11)      not null,
  `created_at`          timestamp    not null default current_timestamp,
  `updated_at`          timestamp    not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);



create table `user_activity_code`
(
  `id`          int(11)       not null auto_increment,
  `name`        varchar(100)  not null,
  `description` varchar(1000) not null,
  `point`       int(11)       not null default 0, -- if not applicable, then 0
  `created_at`  timestamp     not null default current_timestamp,
  `updated_at`  timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`)
);

-- each token hashed with UUID, meaning that each token should be unique.
-- when user successfully resets their password, then the token is stored in this table which means that this token has been used
create table `forgot_password_token`
(
  `id`         int(11)       not null auto_increment,
  `user_id`    int(11)       not null,
  `token`      varchar(2000) not null,
  `created_at` timestamp     not null default current_timestamp,
  `updated_at` timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`)
);



-- user request won't work based on the search and result of this table
-- it is just to keep track of user activity for legal purpose and write only with a few search
create table `user_activity_audit`
(
  `id`                    int(11)       not null auto_increment,
  `user_id`               int(11)       not null,
  `ip_address`            varchar(50)   not null,
  `request_url`           varchar(2048) not null,
  `user_activity_code_id` int(11)       not null,
  `created_at`            timestamp     not null default current_timestamp,
  `updated_at`            timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_activity_code_id` foreign key (`user_activity_code_id`) references `user_activity_code` (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`)
);

create table `user_terms_relation`
(
  `id`         int(11)    not null auto_increment,
  `user_id`    int(11)    not null,
  `terms_id`   int(11)    not null,
  `agreed`     tinyint(1) not null,
  `created_at` timestamp  not null default current_timestamp,
  `updated_at` timestamp  not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`),
  constraint `fk_terms_id` foreign key (`terms_id`) references `terms` (`id`)
);



create table `user_profile_image`
(
  `id`            int(11)       not null auto_increment,
  `user_id`       int(11)       not null,
  `relative_path` varchar(4096) not null,
  `file_type`     varchar(50)   not null,
  `file_name`     varchar(255)  not null,
  `width`         int(11)       not null,
  `height`        int(11)       not null,
  `enabled`       tinyint(1)    not null,
  `created_at`    timestamp     not null default current_timestamp,
  `updated_at`    timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_user_id` foreign key (`user_id`) references `user` (`id`)
);



####################################END OF DATABASE FOR KOKOTRIP##############################################################
####################################END OF DATABASE FOR KOKOTRIP##############################################################
####################################END OF DATABASE FOR KOKOTRIP##############################################################
####################################END OF DATABASE FOR KOKOTRIP##############################################################
####################################END OF DATABASE FOR KOKOTRIP##############################################################
####################################END OF DATABASE FOR KOKOTRIP##############################################################


create table `image`
(
  `id`            binary(16)    not null,
  `_id`           int(11)       not null,
  `relative_path` varchar(4096) not null,
  `file_type`     varchar(10)   not null,
  `file_name`     varchar(255)  not null,
  `width`         int(10)       not null,
  `height`        int(10)       not null,
  `enabled`       tinyint(1)    not null,
  `created_at`    timestamp     not null default current_timestamp,
  `updated_at`    timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk__id` foreign key (`_id`) references `` (`id`)
);


create table ``
(
  `id`         int(11)   not null auto_increment,

  `created_at` timestamp not null default current_timestamp,
  `updated_at` timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_` foreign key (`_id`) references `` (`id`)
);

create table ``
(
  `id`                  int(11)   not null auto_increment,
  `_id`                 int(11)   not null,
  `support_language_id` int(11)   not null,
  `name`                varchar(1),
  `created_at`          timestamp not null default current_timestamp,
  `updated_at`          timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk__id` foreign key (`_id`) references `` (`id`),
  constraint `fk_support_language_id` foreign key (`support_language_id`) references `support_language` (`id`)
);


-- Google map api use lat and long not address
create table `festival`
(
  `id`                       int(11)         not null auto_increment,
  `city_id`                  int(11)         not null,
  `latitude`                 decimal(18, 12) not null,
  `longitude`                decimal(18, 12) not null,
  `website_link`             varchar(2048),
  `contact`                  varchar(1000),
  `average_rate`             decimal(2, 5), -- out of 10
  `number_of_rate`           int(11)         not null,
  `number_of_wishlist_saved` int(11)         not null,
  `popular_score`            decimal(2, 5), -- out of 10
  `created_at`               timestamp       not null default current_timestamp,
  `updated_at`               timestamp       not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_city_id` foreign key (`city_id`) references `city` (`id`)
);

-- think festival takes place every year then you should add new date to the same fetival, that's why this table is needed
-- even if you have festival_time table, you will override the value from festival_time to start_time and end_time for denormalization
-- start_time and end_time null == have not decided / have not decided for multi-langauge at application level not database level?
create table `festival_date`
(
  `id`          int(11)   not null auto_increment,
  `festival_id` int(11)   not null,
  `start_date`  timestamp not null,
  `end_date`    timestamp not null,
  `start_time`  time,
  `end_time`    time,
  `created_at`  timestamp not null default current_timestamp,
  `updated_at`  timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_festival_id` foreign key (`festival_id`) references `festival` (`id`)
);

create table `festival_info`
(
  `id`                  int(11)       not null auto_increment,
  `festival_id`         int(11)       not null,
  `support_language_id` int(11)       not null,
  `name`                varchar(100)  not null,
  `snippet`             varchar(1000) not null,
  `address`             varchar(1000) not null,
  `created_at`          timestamp     not null default current_timestamp,
  `updated_at`          timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_festival_id` foreign key (`festival_id`) references `festival` (`id`),
  constraint `fk_support_language_id` foreign key (`support_language_id`) references `support_language` (`id`)
);

-- if a festival has a single description date, then it will not display date tabs
create table `festival_description`
(
  `id`                  int(11)       not null auto_increment,
  `festival_id`         int(11)       not null,
  `festival_date`       timestamp     not null,
  `support_language_id` int(11)       not null,
  `sub_heading`         varchar(100)  not null,
  `description`         varchar(1000) not null,
  `order`               int(11)       not null,
  `enabled`             tinyint(1)    not null,
  `created_at`          timestamp     not null default current_timestamp,
  `updated_at`          timestamp     not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_festvial_id` foreign key (`festvial_id`) references `festvial` (`id`),
  constraint `fk_support_language_id` foreign key (`support_language_id`) references `support_language` (`id`)
);

create table `festival_description_relation`
(
  `id`                      int(11)   not null auto_increment,
  `festival_id`             int(11)   not null,
  `support_language_id`     int(11)   not null,
  `festival_description_id` int(11)   not null,
  `festival_date`           timestamp not null,
  `created_at`              timestamp not null default current_timestamp,
  `updated_at`              timestamp not null default current_timestamp on update current_timestamp,

  primary key (`id`),
  constraint `fk_festival_id` foreign key (`festival_id`) references `festival` (`id`),
  constraint `fk_support_language_id` foreign key (`support_language_id`) references `support_language` (`id`),
  constraint `fk_festival_description_id` foreign key (`festival_description_id`) references `festival_description` (`id`)
);



-- *************************************** Comment out for the time being because we just focus on korea tour spots only************************************************

-- *************************************** Comment out for the time being because we just focus on korea tour spots only************************************************

-- Asia, North America, South America, Oceania, Africa, Europe
-- create table `continent` (
-- 	`id` int(11) not null auto_increment,
--     `code` varchar(20) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`)
-- );

-- create table `continent_info` (
-- 	`id` int(11) not null auto_increment,
--     `continent_id` int(11) not null,
--     `support_language_id` int(11) not null,
--     `name` varchar(100) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_continent_id` foreign key(`continent_id`) references `continent` (`id`),
--     constraint `fk_support_language_id` foreign key(`support_language_id`) references `support_language` (`id`)
-- );


-- E.g South East Asia,
-- create table `sub_continent` (
-- 	`id` int(11) not null auto_increment,
--     `parent_continent_id` int(11) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`),
--     constraint `fk_continent_id` foreign key(`continent_id`) references `continent`(`id`)
-- );

-- create table `sub_continent_info` (
-- 	`id` int(11) not null auto_increment,
--     `sub_continent_id` int(11) not null,
--     `support_language_id` int(11) not null,
--     `name` varchar(100) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_sub_continent_id` foreign key(`sub_continent_id`) references `sub_continent` (`id`),
--     constraint `fk_support_language_id` foreign key(`support_language_id`) references `support_language` (`id`)
-- );

-- continent_id and sub_continent_id are when you add new city under country, then you need to those two values into city table as well for denormalization
-- sub_continent_id can indicate what its parent continent id is

-- *************************************** Comment out for the time being because we just focus on korea tour spots only************************************************

-- create table `country` (
-- 	`id` int(11) not null auto_increment,
-- --     `sub_continent_id` int(11) not null, -- comment out for the time being because we only do korea tour spots for now
--     `name` varchar(50) not null, -- korean just for management
--     `code` varchar(50) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`)
-- );

-- -- this tbale will be mapped on application level
-- create table `country_info` (
-- 	`id` int(11) not null auto_increment,
--     `country_id` int(11) not null,
--     `support_language_id` int(11) not null,
--     `name` varchar(100) not null,
--     `code` varchar(50) not null, -- denormal
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_country_id` foreign key(`country_id`) references `country` (`id`),
--     constraint `fk_support_language_id` foreign key(`support_language_id`) references `support_language` (`id`)
-- );

-- create table `currency` (
-- 	`id` int(11) not null auto_increment,
-- 	`code` varchar(100) not null,
--     `name` varchar(50) not null, -- korean for management
--     `symbol` varchar(10) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`)
-- );

-- -- this tbale will be mappped to list on application level
-- create table `currency_info` (
-- 	`id` int(11) not null auto_increment,
--     `currency_id` int(11) not null,
--     `code` varchar(100) not null, -- denormal
--     `symbol` varchar(10) not null, -- denormal
--     `support_language_id` int(11) not null,
--     `name` varchar(100),
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_currency_id` foreign key(`currency_id`) references `currency` (`id`),
--     constraint `fk_support_language_id` foreign key(`support_language_id`) references `support_language` (`id`)
-- );

-- -- just for Korean but dont know if we want to expand to have another country
-- -- no performance compromise, we eventurally need to convert Korean to Japanese, Chinese or etc... in city page
-- create table `language` (
-- 	`id` int(11) not null auto_increment,
--     `name` varchar(50) not null, -- korean for management
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`)
-- );

-- -- this table will be mapped on application level
-- create table `language_info` (
-- 	`id` int(11) not null auto_increment,
--     `language_id` int(11) not null,
--     `support_language_id` int(11) not null,
--     `name` varchar(100),
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_language_id` foreign key(`language_id`) references `language` (`id`),
--     constraint `fk_support_language_id` foreign key(`support_language_id`) references `support_language` (`id`)
-- );


-- used for maangement purpose when admin adds new tour_spot and its images, they can choose the season of the image like drop-donw menu
-- also used for display in the tour_spot images like tag summer, fall, spring, winter
-- create table `season` (
-- 	`id` int(11) not null auto_increment,
-- 	`enabled` tinyint(1) not null,
--     `order` int(11) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`)
-- );

-- -- this table will be mapped on application level
-- create table `season_info` (
-- 	`id` int(11) not null auto_increment,
--     `season_id` int(11) not null,
-- 	`support_language_id` int(11) not null,
--     `name` varchar(100) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_season_id` foreign key(`season_id`) references `season` (`id`),
--     constraint `fk_support_language_id` foreign key(`support_language_id`) references `support_language` (`id`)
-- );

-- create table `state_season` (
-- 	`id` int(11) not null auto_increment,
-- 	`state_id` int(11) not null,
--     `january` int(11) not null,
--     `february` int(11) not null,
--     `march` int(11) not null,
--     `april` int(11) not null,
--     `may` int(11) not null,
--     `june` int(11) not null,
--     `july` int(11) not null,
--     `august` int(11) not null,
--     `september` int(11) not null,
--     `october` int(11) not null,
--     `november` int(11) not null,
--     `december` int(11) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`),
--     constraint `fk_january` foreign key(`january_id`) references `season`(`id`),
--     constraint `fk_february` foreign key(`february_id`) references `season`(`id`),
--     constraint `fk_march` foreign key(`march_id`) references `season`(`id`),
--     constraint `fk_april` foreign key(`april_id`) references `season`(`id`),
--     constraint `fk_may` foreign key(`may_id`) references `season`(`id`),
--     constraint `fk_june` foreign key(`june_id`) references `season`(`id`),
--     constraint `fk_july` foreign key(`july_id`) references `season`(`id`),
--     constraint `fk_august` foreign key(`august_id`) references `season`(`id`),
--     constraint `fk_september` foreign key(`september_id`) references `season`(`id`),
--     constraint `fk_october` foreign key(`october_id`) references `season`(`id`),
--     constraint `fk_november` foreign key(`november_id`) references `season`(`id`),
--     constraint `fk_december` foreign key(`december_id`) references `season`(`id`)
-- );

-- *************************************** Comment out for the time being because we just focus on korea tour spots only************************************************
-- we only need korean as langague display in city page

-- create table `city_language_relation` (
-- 	`id` int(11) not null auto_increment,
-- 	`city_id` int(11) not null,
--     `language_id` int(11) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`),
--     constraint `fk_city_id` foreign key(`city_id`) references `city`(`id`),
--     constraint `fk_language_id` foreign key(`language_id`) references `language`(`id`)
-- );

-- create table `price_table` (
-- 	`id` int(11) not null auto_increment,
-- 	`name` varchar(100) not null, -- like adult50child30senior21
--     `currency_id` int(11) not null, -- origin currecny of tour_spot or activity's country
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`),
--     constraint `fk_currency_id` foreign key(`currency_id`) references `currency`(`id`)
-- );


-- create table `price_table_relation` (
-- 	`id` int(11) not null auto_increment,
-- 	`price_table_id` int(11) not null,
--     `price_id` int(11) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
-- 	primary key(`id`),
--     constraint `fk_price_table_id` foreign key(`price_table_id`) references `price_table`(`id`),
--     constraint `fk_price` foreign key(`price_id`) references `price`(`id`)
-- );


-- for management only
-- create table `terms_type` (
-- 	`id` int(11) not null auto_increment,
--     `name` varchar(50) not null,
--     `enabled` tinyint(1) not null,
--     `required` tinyint(1) not null,
--     `order` int not null,
--     `created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`)
-- );

-- create table `terms_type_info` (
-- 	`id` int(11) not null auto_increment,
--     `terms_type_id` int(11) not null,
--     `support_language_id` int(11) not null,
--     `name` varchar(50) not null,
--     `enabled` tinyint(1) not null, -- denormal
--     `order` int(2) not null, -- denormal
--     `required` tinyint(1) not null, -- denormal
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
--     index `support_language_id_enabled` (`support_language_id`, `enabled`),
--     constraint `fk_terms_type_id` foreign key(`terms_type_id`) references `terms_type` (`id`),
--     constraint `fk_support_language_id` foreign key(`support_language_id`) references `support_language` (`id`)
-- );


-- create table `state_info` (
-- 	`id` int(11) not null auto_increment,
--     `enabled` tinyint(1) not null,
--     `state_id` int(11) not null,
--     `support_language_id` int(11) not null,
--     `name` varchar(100) not null,
-- 	`created_at` timestamp not null default current_timestamp,
--     `updated_at` timestamp not null default current_timestamp on update current_timestamp,
--
--     primary key(`id`),
-- 	constraint `fk_state_id_by_state_info` foreign key(`state_id`) references `state` (`id`),
--     constraint `fk_support_language_id_by_state_info` foreign key(`support_language_id`) references `support_language` (`id`)
-- );


# -- if trading_hour_type == closed then display closed, if undefined, then do not display it is for festival
# create table `trading_schedule`
# (
#   `id`         int(11)       not null auto_increment,
#   `name`       varchar(1000) not null,
#   `created_at` timestamp     not null default current_timestamp,
#   `updated_at` timestamp     not null default current_timestamp on update current_timestamp,
#
#   primary key (`id`)
# );


-- when admin add pictures to tour_spot, they should specify the season when the picture was taken, then this table will display as dropdown
# create table `season`
# (
#   `id`         int(11)     not null auto_increment,
#   `order`      int(2)      not null,
#   `month`      int(2)      not null,
#   `name`       varchar(50) not null,
#   `enabled`    tinyint(1)  not null,
#   `created_at` timestamp   not null default current_timestamp,
#   `updated_at` timestamp   not null default current_timestamp on update current_timestamp,
#
#   primary key (`id`)
# );

-- we only need season for korea, therefore this table will be mapped on application level
-- get the month of itinerary's start date as number and get right name based on month, support_langage_id column
# create table `season_info`
# (
#   `id`                  int(11)     not null auto_increment,
#   `enabled`             tinyint(1)  not null, -- denormal
#   `month`               int(2)      not null, -- denormal
#   `season_id`           int(11)     not null,
#   `support_language_id` int(11)     not null,
#   `name`                varchar(50) not null,
#   `created_at`          timestamp   not null default current_timestamp,
#   `updated_at`          timestamp   not null default current_timestamp on update current_timestamp,
#
#   primary key (`id`),
#   constraint `fk_season_id_by_season_info` foreign key (`season_id`) references `season` (`id`),
#   constraint `fk_support_language_id_by_season_info` foreign key (`support_language_id`) references `support_language` (`id`)
#
# );


# # 1. This table includes the tags of activities belonging to a tour_spot and tour_spot tag for searching purpose
# # 2. tour_spot will have @where(isTourSpot=false) annotation on List<tourSpotActivityTagRel>
# create table `tour_spot_activity_tag_rel`
# (
#   `id`           int(11)    not null auto_increment,
#   `enabled`      tinyint(1) not null,
#   `num_of_tag`   int(11)    not null,
#   `tour_spot`    tinyint(1) not null,
#   `tour_spot_id` int(11)    not null,
#   `tag_id`       int(11)    not null,
#   `activity_id`  int(11), # nullable because of tour-spot
#   `created_at`   timestamp  not null default current_timestamp,
#   `updated_at`   timestamp  not null default current_timestamp on update current_timestamp,
#
#   primary key (`id`),
#   constraint `fk_tour_spot_id_by_tour_spot_activity_tag_rel` foreign key (`tour_spot_id`) references `tour_spot` (`id`),
#   constraint `fk_tag_id_by_tour_spot_activity_tag_rel` foreign key (`tag_id`) references `tag` (`id`),
#   constraint `fk_activity_id_by_tour_spot_activity_tag_rel` foreign key (`activity_id`) references `activity` (`id`)
# );


-- *************************************** Comment out for the time being because we just focus on korea tour spots only************************************************


-- *************************************** Comment out for the time being because we just focus on korea tour spots only************************************************


# create database poliocean;
#
# use poliocean;
#
#
# create table tour_spot_info (
#                               id int(11) not null auto_increment,
#                               city_id int(11) not null,
#                               support_language_id int(11) not null,
#                               tag_id int(11) not null,
#
#
#                               primary key (id),
#                               index city_id_support_language_id (tag_id, city_id, support_language_id)
# );
#
# drop table poliocean.tour_spot_info;
#
#
# drop procedure optimizerforin;
#
# create procedure optimizerforin()
# begin
#
#   declare tour_spot_max int unsigned default 100000;
#   declare support_langauge_max int unsigned default 20;
#   declare v_counter int unsigned default 1;
#   declare s_counter int unsigned default 1;
#
#
#   declare city_id_max int unsigned default 100;
#   declare tag_id int unsigned default 50;
#
#   declare city_random int unsigned default 0;
#   declare tag_random int unsigned default 0;
#
#   start transaction;
#   while v_counter <= tour_spot_max do
#
#   set city_random = ROUND((RAND() * (city_id_max-1))+1);
#   set tag_random = ROUND((RAND() * (tag_id-1))+1);
#
#   while s_counter <= support_langauge_max do
#   insert into poliocean.tour_spot_info (id, city_id, support_language_id, tag_id) values (null, city_random, s_counter, tag_random);
#   set s_counter = s_counter + 1;
#   end while;
#
#   set s_counter = 1;
#
#   set v_counter = v_counter + 1;
#
#   end while;
#   commit;
#
# end;
#
# delete from poliocean.tour_spot_info where id > 0;
#
# call optimizerforin();
#
#
# select *
# from poliocean.tour_spot_info
# where city_id = 1 and support_language_id = 2 and tag_id in (1, 2, 3, 4, 5, 6, 7, 8, 9,
#                                                              10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
#                                                              20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
#                                                              30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
#                                                              40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
#                                                              50, 1, 2, 3, 4, 5, 6, 7, 8, 9,
#                                                              10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
#                                                              20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
#                                                              30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
#                                                              40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
#                                                              50)
# order by tag_id;
