package com.zk.plugins;

import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.config.EnablePluginRegistries;

//配置插件，插件写好了，我们要让插件生效！
@Configuration
@EnablePluginRegistries({OrderOperatorPlugin.class})
public class OrderPluginConfig {
}
