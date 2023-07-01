package com.laowengs.kafka2db.adapter.es.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.laowengs.kafka2db.adapter.es.config.EsException;
import com.laowengs.kafka2db.adapter.es.core.EsBaseEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ElasticsearchService {

    @Resource
    private ElasticsearchClient client;

    /**
     * 判断索引是否存在.
     *
     * @param indexName index名称
    */
    public boolean existIndex(String indexName) throws EsException {
        try {
            BooleanResponse booleanResponse = client.indices().exists(e -> e.index(indexName));
            return booleanResponse.value();
        } catch (IOException e) {
            throw new EsException("向es中检测索引【{0}】出错，错误信息为：{1}", indexName, e.getMessage());
        }
    }

    /**
     * 创建索引.
     *
     * @param indexName index名称
    */
    public void createIndex(String indexName) throws EsException {
        try {
            client.indices().create(c -> c.index(indexName));
        } catch (IOException e) {
            throw new EsException("向es中创建索引【{0}】出错，错误信息为：{1}", indexName, e.getMessage());
        }
    }

    /**
     * 添加记录.
     *
     */
    public void addDocument(String indexName,EsBaseEntity document) throws EsException {
        try {
            if (!this.existIndex(indexName)) {
                this.createIndex(indexName);
            }
            client.index(i -> i.index(indexName)
                    .id(document.fetchEsDocumentId())
                    .document(document));
        } catch (IOException e) {
            throw new EsException("向es中添加document出错!", e.getMessage());
        }
    }
    
    /**
     * 添加记录.
     *
     */
    public void addDocument(String indexName,Object document, String id) throws EsException {
        try {
//            if (!this.existIndex(indexName)) {
//                this.createIndex(indexName);
//            }
            client.index(i -> i.index(indexName)
                    .id(id)
                    .document(document));
        } catch (IOException e) {
            throw new EsException("向es中添加document出错!", e);
        }
    }

    public void deleteDocumentById(String indexName,String id) throws EsException {
        try {
            DeleteRequest.Builder builder = new DeleteRequest.Builder();
            builder.id(id).index(indexName);
            client.delete(builder.build());

        } catch (IOException e) {
            throw new EsException("es删除document出错!", e);
        }
    }
    
    /**
     * 批量添加.
     *
     * @param esBaseEntityList 添加的数量集合
     * @param indexName indexName
    */
    public void batchAddDocumentWithId(String indexName,List<? extends EsBaseEntity> esBaseEntityList) throws EsException {
        if (!this.existIndex(indexName)) {
            this.createIndex(indexName);
        }

        BulkRequest.Builder br = new BulkRequest.Builder();
        esBaseEntityList.forEach(esBaseEntity -> br.operations(op -> op
                .index(idx -> idx
                        .index(indexName)
                        .id(esBaseEntity.fetchEsDocumentId())
                        .document(esBaseEntity)
                ))
        );

        try {
            BulkResponse result = client.bulk(br.build());
            if (result.errors()) {
                log.error("Bulk had errors");
                for (BulkResponseItem item : result.items()) {
                    if (item.error() != null) {
                        log.error(item.error().reason());
                    }
                }
            }
        } catch (IOException e) {
            throw new EsException(e, "向es中添加dockument出错");
        }
    }

    /**
     * 批量添加.
     *
     * @param esBaseEntityList 添加的数量集合
     * @param indexName indexName
     */
    public void batchAddDocument(String indexName,List<Object> esBaseEntityList) throws EsException {
        if (!this.existIndex(indexName)) {
            this.createIndex(indexName);
        }

        BulkRequest.Builder br = new BulkRequest.Builder();
        esBaseEntityList.forEach(esBaseEntity -> br.operations(op -> op
                .index(idx -> idx
                        .index(indexName)
                        .document(esBaseEntity)
                ))
        );

        try {
            BulkResponse result = client.bulk(br.build());
            if (result.errors()) {
                log.error("Bulk had errors");
                for (BulkResponseItem item : result.items()) {
                    if (item.error() != null) {
                        log.error(item.error().reason());
                    }
                }
            }
        } catch (IOException e) {
            throw new EsException(e, "向es中添加dockument出错");
        }
    }
    
      /**
     * 根据索引名称和字段查询数据.
     *
     * @param indexName 索引名称
     * @param filedValue 查询字段值
     * @param filedName 查询字段名称
    */
    public List<EsBaseEntity> findApplogs(String indexName, String filedName, String filedValue) throws EsException {
        try {
            SearchResponse<EsBaseEntity> searchResponse = client.search(s -> s.index(indexName)
                            .query(q -> q
                                    .match(t -> t
                                            .field(filedName)
                                            .query(filedValue)
                                    )),
                    EsBaseEntity.class);
            List<Hit<EsBaseEntity>> hitList = searchResponse.hits().hits();
            List<EsBaseEntity> esBaseEntityList = new ArrayList<>();
            for (Hit<EsBaseEntity> mapHit : hitList) {
                esBaseEntityList.add(mapHit.source());
            }
            return esBaseEntityList;
        } catch (IOException e) {
            throw new EsException("【查询 -> 失败】从es中查询分析后的日志出错，错误信息为：{0}", e.getMessage());
        }
    }
    
}

