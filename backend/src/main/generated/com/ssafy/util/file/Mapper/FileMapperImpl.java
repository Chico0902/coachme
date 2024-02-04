package com.ssafy.util.file.Mapper;

import com.ssafy.db.entity.File;
import com.ssafy.util.file.dto.response.FileResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-02T10:39:43+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (ojdkbuild)"
)
public class FileMapperImpl implements FileMapper {

    @Override
    public FileResponseDto fileToFileResponseDto(File file) {
        if ( file == null ) {
            return null;
        }

        FileResponseDto fileResponseDto = new FileResponseDto();

        fileResponseDto.setFileName( file.getName() );
        fileResponseDto.setFileUrl( file.getUrl() );

        return fileResponseDto;
    }

    @Override
    public List<FileResponseDto> fileToFileResponseDtoList(List<File> fileList) {
        if ( fileList == null ) {
            return null;
        }

        List<FileResponseDto> list = new ArrayList<FileResponseDto>( fileList.size() );
        for ( File file : fileList ) {
            list.add( fileToFileResponseDto( file ) );
        }

        return list;
    }
}
