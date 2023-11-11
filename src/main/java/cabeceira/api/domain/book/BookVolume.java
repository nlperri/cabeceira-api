package cabeceira.api.domain.book;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookVolume {
    public String id;
    public String selfLink;
    public VolumeInfo volumeInfo;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class VolumeInfo {
        public String title;
        public List<String> authors;
        public String publishedDate;
        public String description;
        public String publisher;
        public String pageCount;
        public ImageLinks imageLinks;
    }
    @NoArgsConstructor
    @AllArgsConstructor
   public static class ImageLinks{
        public String smallThumbnail;
        public String thumbnail;
    }

}


