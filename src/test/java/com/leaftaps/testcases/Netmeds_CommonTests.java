package com.leaftaps.testcases;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.framework.testng.api.base.ProjectHooks;
import com.leaftaps.pages.MainPage_Netmeds;

public class Netmeds_CommonTests extends ProjectHooks{
	@BeforeTest
	public void setValues() {
		testcaseName = "Netmeds";
		testDescription ="Extract Data from App and store it in Excel";
		authors="Hari";
		category ="Extract Datas";
		excelFileName="SourceData";
	}
	
	@Test(dataProvider = "fetchData")
	public void runTests(String molecule) {
		
		 new MainPage_Netmeds() 
		 .enterMoleculeName_PressEnter(molecule)
		 .scrollTillLastMoleculeResults()
		 .getAll_Alternatives_Of_Molecule_StoreInMap(molecule)
		 .get_AlternativesDetails_StoreInMap(molecule);
		 
		
	}

}
