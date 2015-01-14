//package com.stan.task.test.framework.control;
//
//import java.util.List;
//
//import org.openqa.selenium.WebElement;
//
//import com.stan.task.test.framework.page.AbstractPageModelItem;
//
//public class TextBox extends Element // AbstractTextField
//{
//	private static final String INVALID_CLASS_SUBSTRING = "x-form-invalid-field";
//	private static final String REQUIRED_CLASS_SUBSTRING = "x-form-required-field";
//
//	public TextBox(AbstractPageModelItem parentBrowserItem,
//			ElementLocator elementLocator, String fieldControlName) {
//		super(parentBrowserItem, elementLocator, fieldControlName);
//	}
//
//	public TextBox(AbstractPageModelItem parentBrowserItem,
//			List<ElementLocator> elementLocators, String fieldControlName) {
//		super(parentBrowserItem, elementLocators, fieldControlName);
//	}
//
//	public TextBox(AbstractPageModelItem parentBrowserItem, WebElement element,
//			String controlName) {
//		super(parentBrowserItem, element, controlName);
//	}
//
//	@Override
//	public boolean isRequired() {
//		WebElement inputElement = getInputElement();
//
//		String classValue = inputElement.getAttribute(CLASS_ATTRIBUTE);
//
//		return classValue.contains(REQUIRED_CLASS_SUBSTRING);
//	}
//
//	@Override
//	public boolean isInvalid() {
//		WebElement inputElement = getInputElement();
//
//		String classValue = inputElement.getAttribute(CLASS_ATTRIBUTE);
//
//		return classValue.contains(INVALID_CLASS_SUBSTRING);
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// We use different locators and we cannot always call
//		// Ext.getCmp(arguments[0]) as it may fail
//		// Method will try to find parent element first
//		String elementID = getElementID();
//		if (elementID.contains("-input")) // it can be -inputRow or inputEl
//		{
//			String parentID = elementID.substring(0,
//					elementID.indexOf("-input"));
//			String scriptGetDisabled = String.format(
//					"return Ext.getCmp('%s').disabled", parentID);
//			return !(Boolean) executeScript(scriptGetDisabled);
//		}
//		return !(Boolean) executeScript(
//				"return Ext.getCmp(arguments[0]).disabled", elementID);
//	}
// }