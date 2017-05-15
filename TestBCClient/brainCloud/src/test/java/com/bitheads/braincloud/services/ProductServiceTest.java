package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.ReasonCodes;

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

}