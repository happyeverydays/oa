package com.huizhixin.smart.workflow.camunda.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.camunda.bpm.engine.impl.javax.el.ELContext;
import org.camunda.bpm.engine.impl.javax.el.ELException;
import org.camunda.bpm.engine.impl.javax.el.ELResolver;
import org.camunda.bpm.engine.impl.javax.el.PropertyNotFoundException;
import org.camunda.bpm.engine.impl.javax.el.PropertyNotWritableException;

/**
*
* @author wushuo <2807972772@qq.com>
*
*/
public class CompositeELResolver extends ELResolver {
	private final List<ELResolver> resolvers = new ArrayList<ELResolver>();

	/**
	 * Adds the given resolver to the list of component resolvers. Resolvers are consulted in the
	 * order in which they are added.
	 * 
	 * @param elResolver
	 *            The component resolver to add.
	 * @throws NullPointerException
	 *             If the provided resolver is null.
	 */
	public void add(ELResolver elResolver) {
		if (elResolver == null) {
			throw new NullPointerException("resolver must not be null");
		}
		resolvers.add(elResolver);
	}

	/**
	 * Returns the most general type that this resolver accepts for the property argument, given a
	 * base object. One use for this method is to assist tools in auto-completion. The result is
	 * obtained by querying all component resolvers. The Class returned is the most specific class
	 * that is a common superclass of all the classes returned by each component resolver's
	 * getCommonPropertyType method. If null is returned by a resolver, it is skipped.
	 * 
	 * @param context
	 *            The context of this evaluation.
	 * @param base
	 *            The base object to return the most general property type for, or null to enumerate
	 *            the set of top-level variables that this resolver can evaluate.
	 * @return null if this ELResolver does not know how to handle the given base object; otherwise
	 *         Object.class if any type of property is accepted; otherwise the most general property
	 *         type accepted for the given base.
	 */
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		Class<?> result = null;
		for (ELResolver resolver : resolvers) {
			Class<?> type = resolver.getCommonPropertyType(context, base);
			if (type != null) {
				if (result == null || type.isAssignableFrom(result)) {
					result = type;
				} else if (!result.isAssignableFrom(type)) {
					result = Object.class;
				}
			}
		}
		return result;
	}


	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base) {
		return new Iterator<FeatureDescriptor>() {
			Iterator<FeatureDescriptor> empty = Collections.<FeatureDescriptor> emptyList().iterator();
			Iterator<ELResolver> resolvers = CompositeELResolver.this.resolvers.iterator();
			Iterator<FeatureDescriptor> features = empty;

			Iterator<FeatureDescriptor> features() {
				while (!features.hasNext() && resolvers.hasNext()) {
					features = resolvers.next().getFeatureDescriptors(context, base);
					if (features == null) {
						features = empty;
					}
				}
				return features;
			}

			public boolean hasNext() {
				return features().hasNext();
			}

			public FeatureDescriptor next() {
				return features().next();
			}

			public void remove() {
				features().remove();
			}
		};
	}
	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		context.setPropertyResolved(false);
		for (ELResolver resolver : resolvers) {
			Class<?> type = resolver.getType(context, base, property);
			if (context.isPropertyResolved()) {
				return type;
			}
		}
		return null;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		context.setPropertyResolved(false);
		for (ELResolver resolver : resolvers) {
			Object value = resolver.getValue(context, base, property);
			if (context.isPropertyResolved()) {
				return value;
			}
		}
		return null;
	}


	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		context.setPropertyResolved(false);
		for (ELResolver resolver : resolvers) {
			boolean readOnly = resolver.isReadOnly(context, base, property);
			if (context.isPropertyResolved()) {
				return readOnly;
			}
		}
		return false;
	}

		@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		context.setPropertyResolved(false);
		for (ELResolver resolver : resolvers) {
			resolver.setValue(context, base, property, value);
			if (context.isPropertyResolved()) {
				return;
			}
		}
	}

	
	@Override
	public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object[] params) {
		context.setPropertyResolved(false);
		for (ELResolver resolver : resolvers) {
			Object result = resolver.invoke(context, base, method, paramTypes, params);
			if (context.isPropertyResolved()) {
				return result;
			}
		}
		return null;
	}
}
