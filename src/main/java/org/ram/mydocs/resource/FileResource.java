package org.ram.mydocs.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.api.services.drive.model.File;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class FileResource {
    private String name;
    private String id;
    private String thumbnailLink;
    private String mimeType;
    private List<String> parents;

    public FileResource() {
    }

    public static FileResource from(File file) {
        FileResource resource = new FileResource();
        resource.setId(file.getId());
        resource.setName(file.getName());
        resource.setMimeType(file.getMimeType());
        resource.setParents(file.getParents());
        return resource;
    }
}
