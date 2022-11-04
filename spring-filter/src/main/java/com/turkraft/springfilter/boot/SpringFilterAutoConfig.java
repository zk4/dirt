package com.turkraft.springfilter.boot;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.turkraft.springfilter.FilterParameters;

/**
 * Configuration class used to load properties as an alternative to setting the static fields when
 * using Spring.
 */

@Configuration
public class SpringFilterAutoConfig {

  public SpringFilterAutoConfig(
      @Value("${turkraft.springfilter.dateformatter.pattern:#{null}}") String datePattern,
      @Value("${turkraft.springfilter.localdateformatter.pattern:#{null}}") String localDatePattern,
      @Value("${turkraft.springfilter.localdatetimeformatter.pattern:#{null}}") String localDateTimePattern,
      @Value("${turkraft.springfilter.offsetdatetimeformatter.pattern:#{null}}") String offsetDateTimePattern,
      @Value("${turkraft.springfilter.localtimeformatter.pattern:#{null}}") String localTimeFormatterPattern,
      @Value("${turkraft.springfilter.operator.like.casesensitive:#{null}}") Boolean caseSensitiveLikeOperator) {

    if (datePattern != null) {
      FilterParameters.DATE_FORMATTER.applyPattern(datePattern);
    }

    if (localDatePattern != null) {
      FilterParameters.LOCALDATE_FORMATTER = DateTimeFormatter.ofPattern(localDatePattern);
    }

    if (localDateTimePattern != null) {
      FilterParameters.LOCALDATETIME_FORMATTER = DateTimeFormatter.ofPattern(localDateTimePattern);
    }

    if (offsetDateTimePattern != null) {
      FilterParameters.OFFSETDATETIME_FORMATTER =
          DateTimeFormatter.ofPattern(offsetDateTimePattern);
    }

    if (localTimeFormatterPattern != null) {
      FilterParameters.LOCALTIME_FORMATTER =
          DateTimeFormatter.ofPattern(localTimeFormatterPattern);
    }

    if (caseSensitiveLikeOperator != null) {
      FilterParameters.CASE_SENSITIVE_LIKE_OPERATOR = caseSensitiveLikeOperator;
    }

  }

}
