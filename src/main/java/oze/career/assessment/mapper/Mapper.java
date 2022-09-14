package oze.career.assessment.mapper;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JIDEX
 */
public class Mapper {
    public static <T, E> E convertObject(T source, Class<E> typeDestination) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(source, typeDestination);
    }

    public static <E, T> List<E> convertList(List<T> source, Type destinationType) {

        List<E> model = new ArrayList<>();
        if (source != null && destinationType != null) {

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            for(T rs:source){
                model.add(modelMapper.map(rs, destinationType));
            }

        }

        return model;
    }
}
