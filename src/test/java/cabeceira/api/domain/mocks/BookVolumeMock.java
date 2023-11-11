package cabeceira.api.domain.mocks;

import cabeceira.api.domain.book.BookVolume;

import java.util.ArrayList;
import java.util.List;

public class BookVolumeMock {
    public static BookVolume create() {
        BookVolume.ImageLinks imageLinks = new BookVolume.ImageLinks(
                "some small thumbnail",
                "some thumbnail"
        );

        List<String> authorsList = new ArrayList<>();
        authorsList.add("author1");
        authorsList.add("author2");

        BookVolume.VolumeInfo volumeInfo = new BookVolume.VolumeInfo(
                "some title",
                authorsList,
                "some date",
                "some description",
                "some publisher",
                "100",
                imageLinks
        );

        return new BookVolume(
                "123",
                "some link",
                volumeInfo
        );
    }
}
