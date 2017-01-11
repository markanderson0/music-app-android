package app.application.merch;

import java.util.ArrayList;
import java.util.List;

import app.application.merch.data.MerchService;
import app.application.merch.data.models.merchlistmodel.ConvertedCurrentPrice;
import app.application.merch.data.models.merchlistmodel.FindItemsAdvancedResponse;
import app.application.merch.data.models.merchlistmodel.Item;
import app.application.merch.data.models.merchlistmodel.MerchListModel;
import app.application.merch.data.models.merchlistmodel.SearchResult;
import app.application.merch.data.models.merchlistmodel.SellingStatus;
import app.application.merch.data.models.merchlistmodel.ShippingInfo;
import app.application.merch.data.models.merchlistmodel.ShippingServiceCost;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Mock implementation of the {@link MerchService}
 */
public class MockMerchServiceImpl implements MerchService {
    @Override
    public Observable<MerchListModel> getMerch(@Query("OPERATION-NAME") String operationName, @Query("SERVICE-VERSION") String serviceVersion, @Query("SECURITY-APPNAME") String securityAppname, @Query("RESPONSE-DATA-FORMAT") String responseDataFormat, @Query("keywords") String keywords, @Query("categoryId") String categoryId, @Query("sortOrder") String sortOrder, @Query("outputSelector") String outputSelector, @Query("paginationInput.pageNumber") int page, @Query("itemFilter(0).name") String name, @Query("itemFilter(0).value") String value) {
        return Observable.just(getMerchListModel());
    }

    private MerchListModel getMerchListModel() {
        return new MerchListModel(getFindItemsAdvancedResponse());
    }

    private List<FindItemsAdvancedResponse> getFindItemsAdvancedResponse() {
        List<FindItemsAdvancedResponse> findItemsAdvancedResponses = new ArrayList<>();
        findItemsAdvancedResponses.add(new FindItemsAdvancedResponse(getSearchResult()));
        return findItemsAdvancedResponses;
    }

    private List<SearchResult> getSearchResult() {
        List<SearchResult> searchResult = new ArrayList<>();
        searchResult.add(new SearchResult(getItem()));
        return searchResult;
    }

    private List<Item> getItem() {
        List<Item> item = new ArrayList<>();
        item.add(new Item(getItemId(), getTitle(), getViewItemUrl(), getGalleryUrl(), getSellingStatus(), getShippingInfo()));
        return item;
    }

    private List<String> getItemId(){
        List<String> itemId = new ArrayList<>();
        itemId.add("itemId");
        return itemId;
    }

    private List<String> getTitle(){
        List<String> title = new ArrayList<>();
        title.add("merchItem");
        return title;
    }

    private List<String> getViewItemUrl(){
        List<String> viewItemUrl = new ArrayList<>();
        viewItemUrl.add("viewItemUrl");
        return viewItemUrl;
    }

    private List<String> getGalleryUrl(){
        List<String> galleryUrl = new ArrayList<>();
        galleryUrl.add("galleryUrl");
        return galleryUrl;
    }

    private List<SellingStatus> getSellingStatus(){
        List<SellingStatus> sellingStatus = new ArrayList<>();
        sellingStatus.add(new SellingStatus(getConvertedCurrencyPrice()));
        return sellingStatus;
    }

    private List<ShippingInfo> getShippingInfo(){
        List<ShippingInfo> shippingInfo = new ArrayList<>();
        shippingInfo.add(new ShippingInfo(getShippingServiceCost()));
        return shippingInfo;
    }

    private List<ConvertedCurrentPrice> getConvertedCurrencyPrice() {
        List<ConvertedCurrentPrice> convertedCurrentPrice = new ArrayList<>();
        convertedCurrentPrice.add(new ConvertedCurrentPrice("7"));
        return convertedCurrentPrice;
    }

    private List<ShippingServiceCost> getShippingServiceCost() {
        List<ShippingServiceCost> shippingServiceCost = new ArrayList<>();
        shippingServiceCost.add(new ShippingServiceCost("3"));
        return shippingServiceCost;
    }
}
