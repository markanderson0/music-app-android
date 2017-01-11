package app.application.merch.data;

import app.application.merch.data.models.merchlistmodel.MerchListModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Service to search for merch via the ebay api
 */
public interface MerchService {
    @GET("/services/search/FindingService/v1")
    Observable<MerchListModel> getMerch(
            @Query("OPERATION-NAME") String operationName,
            @Query("SERVICE-VERSION") String serviceVersion,
            @Query("SECURITY-APPNAME") String securityAppname,
            @Query("RESPONSE-DATA-FORMAT") String responseDataFormat,
            @Query("keywords") String keywords,
            @Query("categoryId") String categoryId,
            @Query("sortOrder") String sortOrder,
            @Query("outputSelector") String outputSelector,
            @Query("paginationInput.pageNumber") int page,
            @Query("itemFilter(0).name") String name,
            @Query("itemFilter(0).value") String value
    );
}
