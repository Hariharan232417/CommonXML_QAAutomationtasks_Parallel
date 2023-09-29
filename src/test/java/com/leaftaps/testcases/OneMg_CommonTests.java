package com.leaftaps.testcases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.framework.testng.api.base.ProjectHooks;
import com.leaftaps.pages.OneMG_MainPage;

public class OneMg_CommonTests extends ProjectHooks{
	@BeforeTest
	public void setValues() {
		testcaseName = "OneMg";
		testDescription ="Extract Data from One MG App and store it in Excel - OneMg";
		authors="Hari";
		category ="Extract Datas";
		excelFileName="SourceData";
	}
	
	@Test(dataProvider = "fetchData")
	public void runTests(String molecule) {
		
		new OneMG_MainPage()
		 .closeBanner()
		 .enterMoleculeName_PressEnter(molecule)
		 .getAll_Alternatives_Of_Molecule_StoreInMap(molecule)
		 .get_AlternativesDetails_StoreInMap(molecule);
		 
		
	}
}