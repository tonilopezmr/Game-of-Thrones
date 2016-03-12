/*
 * Copyright 2015 Antonio López Marín <tonilopezmr.com>
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

package es.npatarino.android.gotchallenge.domain.interactor;

import com.tonilopezmr.interactorexecutor.Interactor;

import java.util.List;

/**
 * @author Antonio López.
 */
public interface GetListUseCase<T> extends Interactor {

    interface Callback<T>{
        void onListLoaded(List<T> entityList);
        void onError(Exception exception);
    }

    void execute(final Callback<T> callback);
}
