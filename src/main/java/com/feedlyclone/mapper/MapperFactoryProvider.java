package com.feedlyclone.mapper;

import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides Spring Context with {@link MapperFactory} instance configured with complete mapping metadata. A client
 * creates a needed mapper from the mapper factory in order to convert an entity into the corresponding domain object
 * and vice versa. The mapper performs the conversion based on the configured metadata.
 */
@Component
public class MapperFactoryProvider implements FactoryBean<MapperFactory> {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RssCategoryMapper categoryMapper;

    @Override
    public MapperFactory getObject() throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(Account.class, AccountDTO.class).customize(accountMapper).register();
        mapperFactory.classMap(RssCategory.class, RssCategoryDTO.class).customize(categoryMapper).register();

        return mapperFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return MapperFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
