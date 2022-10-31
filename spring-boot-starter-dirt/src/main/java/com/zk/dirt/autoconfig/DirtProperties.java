package com.zk.dirt.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties(prefix = "dirt")
public class DirtProperties {
    /**
     * Whether to enable Dirt enhanced mode
     */
    private boolean enable= false;

    /**
     * Will use @Entity as if it annotated with @DirtEntity
     * Will use table column as @DirtField
     */
    private boolean smartEntity= false;
}
