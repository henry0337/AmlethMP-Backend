package dev.sh1on.amlethmp.common.annotation;

import dev.sh1on.amlethmp.common.utils.MessageUtils;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MarkerInterfaceProcessor extends AbstractProcessor {
    private final MessageUtils messageUtils;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(Marker.class)) {
            if (elem.getKind() != ElementKind.INTERFACE) continue;

            var type = (TypeElement) elem;
            List<? extends Element> members = type.getEnclosedElements();

            boolean hasInvalid = members.stream().anyMatch(m ->
                    m.getKind() == ElementKind.METHOD ||
                            (m.getKind() == ElementKind.FIELD && !m.getModifiers().contains(Modifier.STATIC)));

            if (hasInvalid) {
                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "@Marker interface must have no methods and no non-static fields/constants",
                        elem);
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Marker.class.getCanonicalName());
    }
}
