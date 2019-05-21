package org.ram.mydocs.resource;

import com.google.api.services.drive.model.File;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileItemResource {
    private String name;
    private String id;
    private String thumbnailLink;

    public static FileItemResource from(File file) {
        return FileItemResource.builder().id(file.getId()).name(file.getName()).thumbnailLink(file.getThumbnailLink()).build();
    }
}
