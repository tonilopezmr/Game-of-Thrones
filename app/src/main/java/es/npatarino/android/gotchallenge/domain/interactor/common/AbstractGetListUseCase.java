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

package es.npatarino.android.gotchallenge.domain.interactor.common;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;


/**
 * @author Antonio López.
 */
public abstract class AbstractGetListUseCase<T> implements GetListUseCase<T> {

    protected Executor executor;
    protected MainThread mainThread;

    protected Callback<T> callback;

    public AbstractGetListUseCase(Executor executor, MainThread mainThread) {
        this.executor = executor;
        this.mainThread = mainThread;
    }

    @Override
    public void execute(final Callback<T> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback parameter can't be null");
        }

        this.callback = callback;
        this.executor.run(this);
    }
}
