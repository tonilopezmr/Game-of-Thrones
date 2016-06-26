package es.npatarino.android.gotchallenge.common.mapper;

import java.util.List;

public interface Mapper<M, E> {
    E map(M model);
    List<E> map(List<M> listModel);
}
