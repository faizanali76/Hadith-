package bll;

public class HadithCollection {
    private int collectionId;
    private String collectionName;
    private String compiler;
    private String description;
    private String edition;
    private String publicationDetails;

    public HadithCollection(int collectionId, String collectionName, String compiler,
                            String description, String edition, String publicationDetails) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.compiler = compiler;
        this.description = description;
        this.edition = edition;
        this.publicationDetails = publicationDetails;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCompiler() {
        return compiler;
    }

    public String getDescription() {
        return description;
    }

    public String getEdition() {
        return edition;
    }

    public String getPublicationDetails() {
        return publicationDetails;
    }
}
