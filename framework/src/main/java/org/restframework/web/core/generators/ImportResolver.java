package org.restframework.web.core.generators;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ImportResolver {

    private final List<String> imports;

    public ImportResolver(SpringComponents component, String apiPackage) {
        this.imports = this.resolve(component, GenericFactory.NO_IMPORT, apiPackage);
    }

    public ImportResolver(SpringComponents component, String genericImportStatement, String apiPackage) {
        this.imports = this.resolve(component, genericImportStatement, apiPackage);
    }

    public  String[] get() {
        return this.imports.toArray(new String[0]);
    }

    @Contract(pure = true)
    private @NotNull List<String> resolve(
            @NotNull SpringComponents component,
            @NotNull String genericImportStatement,
            @NotNull String apiPackage)
    {
        List<String> holder = new ArrayList<>();
        holder.add("import " + apiPackage + ".*");
        holder.add("import org.restframework.web.core.templates.*");
        holder.add("import org.restframework.web.annotations.markers.CompilationComponent");
        holder.add("import java.util.*");

        switch (component) {

            case CONTROLLER -> {
                holder.add("import " + apiPackage + ".service.*");
                holder.add("import lombok.*");
                holder.add("import org.springframework.web.bind.annotation.*");
                holder.add("import org.springframework.http.HttpStatus");
            }
            case SERVICE -> {
                holder.add("import " + apiPackage + ".repository.*");
                holder.add("import lombok.*");
                holder.add("import org.springframework.stereotype.Service");
            }
            case MODEL -> {
                holder.add("import lombok.*");
                holder.add("import jakarta.persistence.*");
            }
            case REPO -> {
                holder.add("import org.springframework.stereotype.Repository");
            }
            case DTO -> {
                holder.add("import lombok.*");
            }
            case NONE -> throw new RestException("");
        }

        return holder;
    }
}
