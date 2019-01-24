package com.ilich.util;

public class View {

    public interface AdvertDetails {
    }

    public interface AuthorDetails {
    }

    public interface FullAdvertDetails extends AdvertDetails, AuthorDetails {
    }
}
