package i2p.lobster.store.dtos;

public class UpdateCategoryDto {
    private String name;
    private String description;

    public UpdateCategoryDto(){}

    public UpdateCategoryDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
