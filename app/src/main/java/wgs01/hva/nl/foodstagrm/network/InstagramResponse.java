package wgs01.hva.nl.foodstagrm.network;

import wgs01.hva.nl.foodstagrm.network.instagramObject.Data;

public class InstagramResponse {

    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }
}
