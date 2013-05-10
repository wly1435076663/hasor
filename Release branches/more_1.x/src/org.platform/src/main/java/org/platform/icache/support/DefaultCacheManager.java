/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.platform.icache.support;
import org.platform.Platform;
import org.platform.context.AppContext;
import org.platform.icache.CacheManager;
import org.platform.icache.ICache;
import org.platform.icache.IKeyBuilder;
import com.google.inject.Singleton;
/**
 * ����ʹ����ڣ������ʵ����ϵͳ�����ṩ��
 * @version : 2013-4-20
 * @author ������ (zyc@byshell.org)
 */
@Singleton
class DefaultCacheManager implements CacheManager {
    private ManagedCacheManager      cacheManager      = null;
    private ManagedKeyBuilderManager keyBuilderManager = null;
    private AppContext               appContext        = null;
    private ICache<Object>           defaultCache      = null;
    private IKeyBuilder              defaultKeyBuilder = null;
    @Override
    public void initManager(AppContext appContext) {
        Platform.info("init CacheManager...");
        this.appContext = appContext;
        //
        this.cacheManager = new ManagedCacheManager();
        this.keyBuilderManager = new ManagedKeyBuilderManager();
        this.cacheManager.initManager(appContext);
        this.keyBuilderManager.initManager(appContext);
        //
        this.defaultCache = appContext.getGuice().getInstance(ICache.class);
        this.defaultKeyBuilder = appContext.getGuice().getInstance(IKeyBuilder.class);
    }
    @Override
    public void destroyManager(AppContext appContext) {
        Platform.info("destroy CacheManager...");
        this.cacheManager.destroyManager(this.appContext);
        this.keyBuilderManager.destroyManager(this.appContext);
    }
    @Override
    public ICache<Object> getDefaultCache() {
        return this.defaultCache;
    }
    @Override
    public ICache<Object> getCache(String cacheName) {
        ICache<Object> icache = this.cacheManager.getCache(cacheName, this.appContext);
        if (icache == null) {
            Platform.warning("use defaultCache . '%s' is not exist.", cacheName);
            return this.defaultCache;
        }
        return icache;
    }
    @Override
    public IKeyBuilder getKeyBuilder(Class<?> sampleType) {
        IKeyBuilder keyBuilder = this.keyBuilderManager.getKeyBuilder(sampleType, this.appContext);
        if (keyBuilder == null) {
            Platform.warning("use defaultKeyBuilder . '%s' is not register.", sampleType);
            return this.defaultKeyBuilder;
        }
        return keyBuilder;
    }
}