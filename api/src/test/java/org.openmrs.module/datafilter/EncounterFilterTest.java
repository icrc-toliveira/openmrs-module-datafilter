/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.datafilter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UsernamePasswordCredentials;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class EncounterFilterTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	private EncounterService encounterService;
	
	@BeforeClass
	public static void beforeClass() {
		Util.loadJavaAgent();
		Util.addFilterAnnotations();
	}
	
	@Before
	public void before() {
		executeDataSet(TestConstants.ROOT_PACKAGE_DIR + "moduleTestData.xml");
		executeDataSet(TestConstants.ROOT_PACKAGE_DIR + "encounters.xml");
	}
	
	@Test
	public void getEncounters_shouldReturnEncountersBelongingToPatientsAccessibleToTheUser() throws Exception {
		Context.logout();
		Context.authenticate(new UsernamePasswordCredentials("dyorke", "test"));
		final String name = "Navuga";
		assertEquals(2, encounterService.getCountOfEncounters(name, false).intValue());
		Collection<Encounter> encounters = encounterService.getEncounters(name, 0, Integer.MAX_VALUE, false);
		assertTrue(TestUtil.containsId(encounters, 1000));
		assertTrue(TestUtil.containsId(encounters, 1001));
	}
	
}
