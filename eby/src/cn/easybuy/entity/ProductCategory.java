package cn.easybuy.entity;
import java.io.Serializable;

/**
 * 商品分类
 */
public class ProductCategory implements Serializable{

	// 编号
	private Integer id;
	
	// 商品类名
	private String name;
	
	// 上一级类名编号
	private Integer parentId;
	
	// 分类级别
	private Integer type;
	
	private String iconClass;
	
	private String parentName;

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}	
}
