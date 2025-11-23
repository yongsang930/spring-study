package com.example.simple_board.crud;


/*
*  dto > entity > dto
* */

import com.example.simple_board.common.Api;
import com.example.simple_board.common.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class CRUDAbstractService<DTO, ENTITY>  implements CRUDInterface<DTO>{

    // 상속받아서 구현한게 없다면 빈 값
    // 매핑되는 Repository를 연결해줌
    @Autowired(required = false)
    private JpaRepository<ENTITY, Long> jpaRepository;

    @Autowired(required = false)
    private Converter<DTO, ENTITY> converter;

    @Override
    public DTO create(DTO dto) {

        // dto > entity
        var entity = converter.toEntity(dto);

        // save
        jpaRepository.save(entity);

        // entity > dto
        // 저장하면서 자동 생성되거나 트리거, 기본값 등이 있기 때문에 기존 파라미터 dto가 아닌 entity > dto 후 return
        return converter.toDto(entity);
    }

    @Override
    public Optional<DTO> read(Long id) {

        var optionalEntity = jpaRepository.findById(id);

        var dto = optionalEntity.map(
                it -> {
                    return converter.toDto(it);
                }
        ).orElseGet(()->null);

        return Optional.ofNullable(dto);
    }

    @Override
    public DTO update(DTO dto) {
        var entity = converter.toEntity(dto);
        jpaRepository.save(entity);
        return converter.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Api<List<DTO>> list(Pageable pageable) {

        var list = jpaRepository.findAll(pageable);

        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build()
                ;

        var dtoList = list.stream()
                .map(it -> {
                    return converter.toDto(it);
                }).collect(Collectors.toList());

        var res = Api.<List<DTO>>builder()
                .body(dtoList)
                .pagination(pagination)
                .build();

        return res;
    }
}
