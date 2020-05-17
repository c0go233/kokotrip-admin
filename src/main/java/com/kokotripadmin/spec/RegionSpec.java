package com.kokotripadmin.spec;

import com.kokotripadmin.entity.city.*;
import com.kokotripadmin.entity.region.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;

public class RegionSpec {

    public static Specification<Region> findByEnabled(boolean regionEnabled) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Region_.id)));
            return criteriaBuilder.equal(root.get(Region_.enabled), regionEnabled);
        };
    }

    public static Specification<Region> findByName(String regionName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Region_.name), regionName);
        };
    }

    public static Specification<Region> findByCityEnabled(boolean cityEnabled) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Region_.city).get(City_.enabled), cityEnabled);
        };
    }

    public static Specification<Region> findAllByEnabledAndCityEnabled(boolean regionEnabled, boolean cityEnabled) {
        return Specification.where(findByEnabled(regionEnabled)).and(findByCityEnabled(cityEnabled));
    }


    public static Specification<RegionInfo> findInfoById(Integer regionId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(RegionInfo_.regionId), regionId);
        };
    }

    public static Specification<RegionInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(RegionInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<RegionInfo> findInfoByIdAndSupportLanguageId(Integer regionId, Integer supportLanguageId) {
        return Specification.where(findInfoById(regionId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }




    public static Specification<RegionThemeRel> findThemeRelById(Integer regionId, boolean fetchThemeTagRel) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (fetchThemeTagRel) {
                root.fetch(RegionThemeRel_.regionThemeTagRelList.getName(), JoinType.LEFT);
                criteriaQuery.distinct(true);
            }
            return criteriaBuilder.equal(root.get(RegionThemeRel_.regionId), regionId);
        };
    }

    public static Specification<RegionThemeRel> findThemeRelByThemeId(Integer themeId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(RegionThemeRel_.themeId), themeId);
        };
    }

    public static Specification<RegionThemeRel> findThemeRelByIdAndThemeId(Integer regionId, Integer themeId) {
        return Specification.where(findThemeRelById(regionId, false)).and(findThemeRelByThemeId(themeId));
    }



    public static Specification<RegionThemeTagRel> findThemeTagRelByThemeRelId(Integer themeId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(RegionThemeTagRel_.regionThemeRelId), themeId);
        };
    }

    public static Specification<RegionThemeTagRel> findThemeTagRelByTagId(Integer tagId, boolean fetchThemeRel, boolean fetchRegion) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            if (fetchThemeRel) {
                Fetch<RegionThemeTagRel, RegionThemeRel> fetch =
                        root.fetch(RegionThemeTagRel_.regionThemeRel.getName(), JoinType.LEFT);

                if (fetchRegion)
                    fetch.fetch(RegionThemeRel_.region.getName(), JoinType.LEFT);

                criteriaQuery.distinct(true);
            }

            return criteriaBuilder.equal(root.get(RegionThemeTagRel_.tagId), tagId);
        };
    }

    public static Specification<RegionThemeTagRel> findThemeTagRelByThemeRelIdAndTagId(Integer themeId, Integer tagId) {
        return Specification.where(findThemeTagRelByThemeRelId(themeId)).and(findThemeTagRelByTagId(tagId, false, false));
    }

//
//    public static Specification<RegionThemeTagRel> findThemeTagRelById(Integer id) {
//        return (root, criteriaQuery, criteriaBuilder) -> {
//            return criteriaBuilder.equal(root.get(RegionThemeTagRel_.region))
//        }
//    }
}
