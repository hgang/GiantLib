package com.android.volley.manager;

import com.android.volley.Request;

/**
 * Abstract LoaderControler that implements LoadControler & Volley Listener & ErrorListener
 *
 * @author steven pan
 */
public abstract class AbsLoadController implements LoadController {

    protected Request<?> mRequest;

    public void bindRequest(Request<?> request) {
        this.mRequest = request;
    }

    @Override
    public void cancel() {
        if (this.mRequest != null) {
            this.mRequest.cancel();
        }
    }

    protected String getOriginUrl() {
        return this.mRequest.getOriginUrl();
    }
}