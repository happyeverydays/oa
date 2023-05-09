package com.huizhixin.smart.workflow.camunda.el;

import java.util.Map;

import org.camunda.bpm.engine.impl.el.Expression;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.el.ReadOnlyMapELResolver;
import org.camunda.bpm.engine.impl.el.VariableContextElResolver;
import org.camunda.bpm.engine.impl.el.VariableScopeElResolver;
import org.camunda.bpm.engine.impl.javax.el.ArrayELResolver;
import org.camunda.bpm.engine.impl.javax.el.BeanELResolver;
import org.camunda.bpm.engine.impl.javax.el.ELResolver;
import org.camunda.bpm.engine.impl.javax.el.ListELResolver;
import org.camunda.bpm.engine.impl.javax.el.MapELResolver;
import org.camunda.bpm.engine.impl.javax.el.ValueExpression;
import org.camunda.bpm.engine.spring.ApplicationContextElResolver;
import org.springframework.context.ApplicationContext;

/**
*
* @author wushuo <2807972772@qq.com>
*
*/
public class SpringExpressionManager extends ExpressionManager {
	protected ApplicationContext applicationContext;

	/**
	 * @param applicationContext the applicationContext to use. Ignored when 'beans'
	 *                           parameter is not null.
	 * @param beans              a map of custom beans to expose. If null, all beans
	 *                           in the application-context will be exposed.
	 */
	public SpringExpressionManager(ApplicationContext applicationContext, Map<Object, Object> beans) {
		super(beans);
		this.applicationContext = applicationContext;
	}

	@Override
	protected ELResolver createElResolver() {
		CompositeELResolver compositeElResolver = new CompositeELResolver();
		compositeElResolver.add(new VariableScopeElResolver());
		compositeElResolver.add(new VariableContextElResolver());

		if (beans != null) {
			// Only expose limited set of beans in expressions
			compositeElResolver.add(new ReadOnlyMapELResolver(beans));
		} else {
			// Expose full application-context in expressions
			compositeElResolver.add(new ApplicationContextElResolver(applicationContext));
		}

		compositeElResolver.add(new ArrayELResolver());
		compositeElResolver.add(new ListELResolver());
		compositeElResolver.add(new MapELResolver());
		compositeElResolver.add(new BeanELResolver());

		return compositeElResolver;
	}

	@Override
	public Expression createExpression(String expression) {
		ValueExpression valueExpression = createValueExpression(expression);
		return new JuelExpression(valueExpression, this, expression);
	}
}
