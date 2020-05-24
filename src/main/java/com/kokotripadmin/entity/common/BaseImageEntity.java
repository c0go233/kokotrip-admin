package com.kokotripadmin.entity.common;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.boot.model.naming.ImplicitTenantIdColumnNameSource;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseImageEntity extends BaseEntity {

    @Column(name = "name")
    protected String name;

    @Column(name = "enabled")
    protected boolean enabled;

    @Column(name = "\"order\"")
    protected Integer order;

    @Column(name = "bucket_key")
    protected String bucketKey;

    @Column(name = "file_type")
    protected String fileType;

    @Column(name = "rep_image")
    protected boolean repImage;

//    @Column(name = "width")
//    private int width;
//
//    @Column(name = "height")
//    private int height;
}
