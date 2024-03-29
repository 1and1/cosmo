/*
 * Copyright 2006 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unitedinternet.cosmo.model.hibernate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Target;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Hibernate persistent Attribute.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Define indexes on discriminator and key fields
@Table(name = "attribute", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "itemid", "namespace", "localname" }) }, indexes = {
                @Index(name = "idx_attrtype", columnList = "attributetype"),
                @Index(name = "idx_attrname", columnList = "localname"),
                @Index(name = "idx_attrns", columnList = "namespace") })
@DiscriminatorColumn(name = "attributetype", discriminatorType = DiscriminatorType.STRING, length = 16)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public abstract class HibAttribute extends HibAuditableObject implements Attribute {

    private static final long serialVersionUID = 3927093659569283339L;

    // Fields
    @Embedded
    @Target(HibQName.class)
    private QName qname;

    @ManyToOne(targetEntity = HibItem.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid", nullable = false)
    private Item item;

    // Constructors
    /** default constructor */
    public HibAttribute() {
    }

    @Override
    public QName getQName() {
        return qname;
    }

    @Override
    public void setQName(QName qname) {
        this.qname = qname;
    }

    @Override
    public String getName() {
        if (qname == null) {
            return null;
        }

        return qname.getLocalName();
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public abstract Attribute copy();

    /**
     * Return string representation
     */
    public String toString() {
        Object value = getValue();
        if (value == null) {
            return "null";
        }
        return value.toString();
    }

    public abstract void validate();
}
