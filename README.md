# Read Me

@Filter allows me to set WHERE clause

The original idea is from  
https://salahuddin-s.medium.com/supporting-translations-in-your-spring-boot-application-using-jpa-hibernate-filters-95f946a0f1cb

Another example from  
https://medium.com/@malvin.lok/deep-dive-understanding-jpas-filter-annotation-with-examples-74d31e2667c4  
@FilterDef defines a filter named "tenantFilter" that accepts a parameter "tenantId" of type long  
@Filter applies the condition: records are only returned if their tenant_id matches the provided parameter  
The condition tenant_id = :tenantId works like a WHERE clause in SQL

````java
@Entity
@FilterDef(name = "tenantFilter", 
    parameters = @ParamDef(name = "tenantId", type = "long"))
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class Document {
    @Id
    private Long id;
    private String name;
    private String content;
    
    @Column(name = "tenant_id")
    private Long tenantId;
}

@Service
@Transactional
public class DocumentService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    public List<Document> getDocumentsForTenant(Long tenantId) {
        // Enable the filter
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("tenantFilter");
        filter.setParameter("tenantId", tenantId);
        
        try {
            return documentRepository.findAll();
        } finally {
            // Always disable the filter after use
            session.disableFilter("tenantFilter");
        }
    }
}
```

