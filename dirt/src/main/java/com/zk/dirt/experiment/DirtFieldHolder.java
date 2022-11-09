package com.zk.dirt.experiment;

import lombok.Getter;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;

public class DirtFieldHolder {
	@Getter
	private final Field field;
	private final StandardEvaluationContext evaluationContext;
	private final Expression expression;
	public DirtFieldHolder(Field field, BeanFactoryResolver beanFactoryResolver, String targetEl) {

		this.field = field;
		ExpressionParser parser = new SpelExpressionParser();
		TemplateParserContext templateParserContext = new TemplateParserContext();
		this.expression = parser.parseExpression(targetEl, templateParserContext);
		this.evaluationContext = new StandardEvaluationContext();
		this.evaluationContext.setBeanResolver(beanFactoryResolver);
	}
	public Object loadData(Object o) {
		Object value = expression.getValue(evaluationContext, o);
		return value;
	}

}
