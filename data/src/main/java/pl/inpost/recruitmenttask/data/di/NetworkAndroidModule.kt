package pl.inpost.recruitmenttask.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.mock.ApiTypeAdapter
import pl.inpost.recruitmenttask.data.database.dao.ShipmentDAO
import pl.inpost.recruitmenttask.data.mock.MockShipmentApi
import pl.inpost.recruitmenttask.data.shipments.ShipmentApi
import pl.inpost.recruitmenttask.data.shipments.ShipmentGateway
import pl.inpost.recruitmenttask.domain.shipments.ShipmentRepository

@InstallIn(SingletonComponent::class)
@Module
internal class NetworkAndroidModule {
    @Provides
    fun shipmentApi(
        @ApplicationContext context: Context,
        apiTypeAdapter: ApiTypeAdapter
    ): ShipmentApi = MockShipmentApi(context, apiTypeAdapter)

    @Provides
    fun providesShipmentRepository(
        shipmentApi: ShipmentApi,
        shipmentDAO: ShipmentDAO
    ): ShipmentRepository = ShipmentGateway(shipmentApi, shipmentDAO)
}
