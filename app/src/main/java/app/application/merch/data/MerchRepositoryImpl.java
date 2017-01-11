package app.application.merch.data;

import java.io.IOException;

import app.application.merch.data.models.merchlistmodel.MerchListModel;
import rx.Observable;

/**
 * Implementation of {@link MerchRepository}
 */
public class MerchRepositoryImpl implements MerchRepository {

    private static final String OPERATION_NAME = "findItemsAdvanced";
    private static final String SERVICE_VERSION = "1.0.0";
    private static final String SECURITY_APPNAME = "XXXXXS";
    private static final String RESPONSE_DATA_FORMAT = "JSON";
    private static final String OUTPUT_SELECTOR = "AspectHistogram";
    private static final String ITEM_FILTER_NAME = "Condition";
    private static final String ITEM_FILTER_VALUE = "New";

    MerchService merchService;

    public MerchRepositoryImpl(MerchService merchService) {
        this.merchService = merchService;
    }

    /**
     * Returns the artists found from the query. If the user has no internet connection an
     * IOException is thrown by retrofit
     *
     * @param keywords name of the merch item to be queried
     * @param categoryId the category of the merch
     * @param sortOrder order to sort the merch
     * @param page page of the query
     * @return merch found from the query
     */
    @Override
    public Observable<MerchListModel> getMerch(String keywords, String categoryId, String sortOrder, int page) {
        return Observable.defer(() -> merchService.getMerch(
                OPERATION_NAME,
                SERVICE_VERSION,
                SECURITY_APPNAME,
                RESPONSE_DATA_FORMAT,
                keywords,
                categoryId,
                sortOrder,
                OUTPUT_SELECTOR,
                page,
                ITEM_FILTER_NAME,
                ITEM_FILTER_VALUE
        )).retryWhen(
                observable ->
                        observable.flatMap(o -> {
                            if (o instanceof IOException) {
                                return Observable.just(null);
                            }
                            return Observable.error(o);
                        }));
    }
}
