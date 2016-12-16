package es.npatarino.android.gotchallenge.base.mapper;

import java.util.List;

public interface TwoWaysMapper<M, E> extends Mapper<M, E> {
  M inverseMap(E model);

  List<M> inverseMap(List<E> listModel);
}
