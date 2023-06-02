package pl.inpost.recruitmenttask.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import pl.inpost.recruitmenttask.R

class NetworkChanges(view: View) {

    val networkConnection = MutableLiveData<Boolean>()

    private var snackbar =
        Snackbar.make(view, R.string.no_internet_title, Snackbar.LENGTH_INDEFINITE)

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    init {
        snackbar.setAction(R.string.hide) {
            snackbar.dismiss()
        }
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            networkConnection.postValue(true)
            snackbar.dismiss()
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                networkConnection.postValue(false)
                snackbar.show()
            } else {
                networkConnection.postValue(true)
                snackbar.dismiss()
            }
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            networkConnection.postValue(false)
            snackbar.show()
        }
    }

    fun checkInternetConnection(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}