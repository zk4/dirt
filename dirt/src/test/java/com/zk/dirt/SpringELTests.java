package com.zk.dirt;

import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.experiment.DirtFieldHolder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SpringELTests {

	@Autowired
	ApplicationContext  applicationContext;

	private DirtFieldHolder createFromField(Field field){
		DirtField mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, DirtField.class);
		if (mergedAnnotation == null){
			return null;
		}

		String el = mergedAnnotation.el();

		String targetEl = el;
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations){
			AnnotationAttributes mergedAnnotationAttributes = AnnotatedElementUtils.findMergedAnnotationAttributes(field, annotation.annotationType(), true, true);
			for (Map.Entry<String, Object> entry : mergedAnnotationAttributes.entrySet()){
				String key = "${" + entry.getKey() + "}";
				String value = String.valueOf(entry.getValue());
				targetEl = targetEl.replace(key, value);
			}
		}

		return new DirtFieldHolder(field, new BeanFactoryResolver(applicationContext), targetEl);
	}
	public List<DirtFieldHolder> createFor(Class cls) {
		return FieldUtils.getAllFieldsList(cls).stream()
				.map(this::createFromField)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
	@Test
	void name() {
		List<DirtFieldHolder> aFor = createFor(MetaType2.class);
		for (DirtFieldHolder dirtFieldHolder : aFor) {
			Object o = dirtFieldHolder.loadData(1);
			System.out.println(o);
		}
	}
}
