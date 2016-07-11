package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class ProductServiceTest extends TestFixtureBase
{
    private final String _currencytype = "credits";
    private final String _platform = "windows";
    private final String _productCatagory = "Test";

    @Test
    public void testGetCurrency() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getProductService().getCurrency(
                _currencytype,
                tr);

        tr.Run();
    }

    @Test
    public void testAwardCurrency() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getProductService().awardCurrency(
                _currencytype,
                200,
                tr);

        tr.Run();
    }

    @Test
    public void testConsumeCurrency() throws Exception
    {
        testAwardCurrency();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getProductService().consumeCurrency(
                _currencytype,
                100,
                tr);

        tr.Run();
    }

    @Test
    public void testResetCurrency() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getProductService().resetCurrency(
                tr);

        tr.Run();
    }

    @Test
    public void testGetSalesInventory() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getProductService().getSalesInventory(
                _platform,
                "CAD",
                tr);

        tr.Run();
    }

    @Test
    public void testGetSalesInventoryByCategory() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getProductService().getSalesInventoryByCategory(
                _platform,
                "CAD",
                _productCatagory,
                tr);

        tr.Run();
    }

    @Test
    public void testGetEligiblePromotions() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getProductService().getEligiblePromotions(
                tr);

        tr.Run();
    }

    @Test
    public void testConfirmGooglePlayPurchase() throws Exception
    {
// TODO: 15-09-03  
    }

    @Test
    public void testAwardParentCurrency() throws Exception
    {
        goToChildProfile();
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getProductService().awardParentCurrency(_currencytype, 1000, m_parentLevelName, tr);
        tr.Run();
    }

    @Test
    public void testConsumeParentCurrency() throws Exception
    {
        goToChildProfile();
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getProductService().consumeParentCurrency(_currencytype, 1000, m_parentLevelName, tr);
        tr.Run();
    }

    @Test
    public void testGetParentCurrency() throws Exception
    {
        goToChildProfile();
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getProductService().getParentCurrency(_currencytype, m_parentLevelName, tr);
        tr.Run();
    }

    @Test
    public void testResetParentCurrency() throws Exception
    {
        goToChildProfile();
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getProductService().resetParentCurrency(m_parentLevelName, tr);
        tr.Run();
    }
}