package com.docum.view;

import java.util.HashSet;
import java.util.Set;

import com.docum.util.AlgoUtil;

public abstract class AbstractDlgView {
	private Set<DialogActionHandler> handlers = new HashSet<DialogActionHandler>();
	
	public void addHandler(DialogActionHandler handler) {
		handlers.add(handler);
	}
	public void removeHandler(DialogActionHandler handler) {
		handlers.remove(handler);
	}
	protected <T extends AbstractDlgView> void fireAction(final T dialog,
			final DialogActionEnum action) {
		AlgoUtil.forEach(handlers, new AlgoUtil.ApplyFunctor<DialogActionHandler>(){
			@Override
			public void apply(DialogActionHandler handler) {
				handler.handleAction(dialog, action);
			}
		});
	}
}
