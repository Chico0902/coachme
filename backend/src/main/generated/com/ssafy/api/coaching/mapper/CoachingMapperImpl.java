package com.ssafy.api.coaching.mapper;

import com.ssafy.api.coaching.dto.GetOneCoachingResponseDto;
import com.ssafy.db.entity.Category;
import com.ssafy.db.entity.Coaching;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-02T10:39:43+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (ojdkbuild)"
)
public class CoachingMapperImpl implements CoachingMapper {

    @Override
    public GetOneCoachingResponseDto coachingToGetOneCoachingResponseDto(Coaching coaching) {
        if ( coaching == null ) {
            return null;
        }

        GetOneCoachingResponseDto getOneCoachingResponseDto = new GetOneCoachingResponseDto();

        getOneCoachingResponseDto.setMain( coachingMainCategoryName( coaching ) );
        getOneCoachingResponseDto.setSub( coachingSubCategoryName( coaching ) );
        getOneCoachingResponseDto.setName( coaching.getName() );
        getOneCoachingResponseDto.setSummary( coaching.getSummary() );
        getOneCoachingResponseDto.setHtmlDocs( coaching.getHtmlDocs() );

        return getOneCoachingResponseDto;
    }

    private String coachingMainCategoryName(Coaching coaching) {
        if ( coaching == null ) {
            return null;
        }
        Category mainCategory = coaching.getMainCategory();
        if ( mainCategory == null ) {
            return null;
        }
        String name = mainCategory.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String coachingSubCategoryName(Coaching coaching) {
        if ( coaching == null ) {
            return null;
        }
        Category subCategory = coaching.getSubCategory();
        if ( subCategory == null ) {
            return null;
        }
        String name = subCategory.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
