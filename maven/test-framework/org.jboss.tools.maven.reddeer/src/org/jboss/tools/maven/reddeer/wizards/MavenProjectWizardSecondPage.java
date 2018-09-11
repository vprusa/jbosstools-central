/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.maven.reddeer.wizards;

import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.TableHasRows;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

import java.util.List;

public class MavenProjectWizardSecondPage extends WizardPage{
	
	public MavenProjectWizardSecondPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	public void selectArchetype(String catalog, final String archetype){
		selectArchetype(catalog, archetype, null);
	}

	public void selectArchetype(String catalog, final String archetype, final String groupId){
		new DefaultCombo(0).setSelection(catalog);
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
		new WaitUntil(new TableHasRows(new DefaultTable()),TimePeriod.LONG);
		DefaultTable table = new DefaultTable();
				 	 	    
	    @SuppressWarnings("unchecked")
		List<TableItem> items = table.getItems(new Matcher<TableItem>() {
			@Override
			public void describeTo(Description arg0) {}
			@Override
			public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {}
			@Override
			public void describeMismatch(Object arg0, Description arg1) {}
			@Override
			public boolean matches(Object arg0) {
				DefaultTableItem ti = (DefaultTableItem)arg0;
				// archetype is necessary, groupId is optional but if set it has to match
				return ((groupId != null && ti.getText(0).matches(".*"+groupId+".*")) || groupId == null) && ti.getText(1).matches(".*"+archetype+".*");
			}});
	    items.get(0).select();
	}

}
