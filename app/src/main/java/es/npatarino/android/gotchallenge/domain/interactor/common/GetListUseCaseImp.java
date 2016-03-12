/*
 * Copyright 2015 Antonio López Marín <tonilopezmr.github.io>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.npatarino.android.gotchallenge.domain.interactor.common;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GotHouseRepository.Repository;

/**
 * @author Antonio López.
 */
public class GetListUseCaseImp<T> extends AbstractGetListUseCase<T> implements GetListUseCase<T> {

    protected Repository repository;

    public GetListUseCaseImp(Executor executor, MainThread mainThread, Repository<T> repository) {
        super(executor, mainThread);
        this.repository = repository;
    }

    @Override
    public void run() {
        try {
            final List list = repository.getList();
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onListLoaded(list);
                }
            });
        } catch (final Exception e) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(e);
                }
            });
            e.printStackTrace();
        }
    }
}
