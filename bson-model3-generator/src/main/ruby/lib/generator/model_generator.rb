require_relative 'imports_generator'
require_relative 'consts_generator'
require_relative 'store_data'
require_relative 'properties_generator'
require_relative 'clean_fields_generator'
require_relative 'append_field_updates_generator'
require_relative 'append_updated_data_generator'
require_relative 'to_display_data_generator'
require_relative 'to_bson_value_generator'
require_relative 'load_generator'
require_relative 'to_store_data_generator'
require_relative 'load_store_data_generator'
require_relative 'any_updated_generator'
require_relative 'deleted_data_generator'
require_relative 'deleted_generator'
require_relative 'deep_copy_from_generator'
require_relative 'to_string_generator'


class ModelGenerator

  attr_reader :config,
              :model_conf,
              :model_name,
              :store_fields,
              :imports_generator,
              :consts_generator,
              :properties_generator,
              :clean_fields_generator,
              :append_field_updates_generator,
              :append_updated_data_generator,
              :to_display_data_generator,
              :to_bson_value_generator,
              :load_generator,
              :to_store_data_generator,
              :load_store_data_generator,
              :any_updated_generator,
              :deleted_data_generator,
              :deleted_generator,
              :deep_copy_from_generator,
              :to_string_generator

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @model_name = model_conf.name
    @store_fields = model_conf.fields.filter { |field_conf| field_conf.store_field? }
    @imports_generator = ImportsGenerator.new(@config, @model_conf)
    @consts_generator = ConstsGenerator.new(@config, @model_conf)
    @properties_generator = PropertiesGenerator.new(@config, @model_conf)
    @has_children = @store_fields.any? { |field_conf| field_conf.has_children? }
    @clean_fields_generator = CleanFieldsGenerator.new(@config, @model_conf)
    @append_field_updates_generator = AppendFieldUpdatesGenerator.new(@config, @model_conf)
    @append_updated_data_generator = AppendUpdatedDataGenerator.new(@config, @model_conf)
    @to_display_data_generator = ToDisplayDataGenerator.new(@config, @model_conf)
    @to_bson_value_generator = ToBsonValueGenerator.new(@config, @model_conf)
    @load_generator = LoadGenerator.new(@config, @model_conf)
    @to_store_data_generator = ToStoreDataGenerator.new(@config, @model_conf)
    @load_store_data_generator = LoadStoreDataGenerator.new(@config, @model_conf)
    @any_updated_generator = AnyUpdatedGenerator.new(@config, @model_conf)
    @deleted_data_generator = DeletedDataGenerator.new(@config, @model_conf)
    @deleted_generator = DeletedGenerator.new(@config, @model_conf) 
    @deep_copy_from_generator = DeepCopyFromGenerator.new(@config, @model_conf)
    @to_string_generator = ToStringGenerator.new(@config, @model_conf)
  end

  def generate
    code = generate_class_prefix_code
    code << generate_consts_code
    code << generate_store_data_class_code
    code << generate_properties_code
    code << generate_methods_code
    code << generate_class_suffix_code
  end

  def has_children?
    @has_children
  end

  private
  def generate_class_prefix_code
    code = generate_package_code
    code << "\n"
    code << generate_imports_code
    code << "\n"
    code << generate_class_declaration_code
  end

  def generate_package_code
    "package #{@config.package};\n"
  end

  def generate_imports_code
    @imports_generator.generate
  end

  def generate_class_declaration_code
    code = "@NullMarked\n"
    code << "public final class #{@model_name} extends #{generic_super_type} {\n"
  end

  def generic_super_type
    case @model_conf.type
    when 'object'
      "AbstractObjectModel<#{@model_name}>"
    when 'root'
      "AbstractRootModel<#{@model_name}>"
    else
      raise ArgumentError, "Unknown model type: #{@model_conf.type}"
    end
  end

  def generate_consts_code
    @consts_generator.generate
  end

  def generate_store_data_class_code
    StoreData::generate_class_code(@config, @model_conf)
  end

  def generate_properties_code
    @properties_generator.generate_properties_code
  end

  def generate_methods_code
    code = ''
    if @model_conf.type == 'root'
      code << generate_store_data_type_code
    end
    if has_children?
      code << generate_reset_children_code
    end
    code << generate_clean_fields_code
    code << generate_append_field_updates_code
    code << generate_append_updated_data_code
    code << generate_to_display_data_code
    code << generate_to_bson_value_code
    code << generate_load_code
    code << generate_to_store_data_code
    code << generate_load_store_data_code
    code << generate_any_updated_code
    code << generate_deleted_code
    code << generate_deep_copy_code
    code << generate_deep_copy_from_code
    code << generate_to_string_code
  end

  def generate_store_data_type_code
    code = "\n"
    code << "    @Override\n"
    code << "    protected Class<#{@model_name}StoreData> storeDataType() {\n"
    code << "        return #{@model_name}StoreData.class;\n"
    code << "    }\n"
  end

  def generate_reset_children_code
    code = "\n"
    code << "    @Override\n"
    code << "    protected #{@model_name} resetChildren() {\n"
    @store_fields.each do |field_conf|
      if field_conf.has_children?
        code << "        #{field_conf.name}.reset();\n"
      end
    end
    code << "        return this;\n"
    code << "    }\n"
  end

  def generate_clean_fields_code
    code = "\n"
    code << @clean_fields_generator.generate
  end

  def generate_append_field_updates_code
    code = "\n"
    code << @append_field_updates_generator.generate
  end

  def generate_append_updated_data_code
    code = "\n"
    code << @append_updated_data_generator.generate
  end

  def generate_to_display_data_code
    code = "\n"
    code << @to_display_data_generator.generate
  end

  def generate_to_bson_value_code
    code = "\n"
    code << @to_bson_value_generator.generate
  end

  def generate_load_code
    code = "\n"
    code << @load_generator.generate
  end

  def generate_to_store_data_code
    code = "\n"
    code << @to_store_data_generator.generate
  end

  def generate_load_store_data_code
    code = "\n"
    code << @load_store_data_generator.generate
  end

  def generate_any_updated_code
    code = "\n"
    code << @any_updated_generator.generate
  end

  def generate_deleted_code
    code = ''
    if @deleted_data_generator.field_confs.empty?
      code << "\n"
      code << "    @Override\n"
      code << "    public @Nullable Map<String, ?> toDeleted() {\n"
      code << "        return null;\n"
      code << "    }\n"
    else
      code << "\n"
      code << @deleted_data_generator.generate_append_deleted_data_code
    end
    if @deleted_generator.field_confs.empty?
      code << "\n"
      code << "    @Override\n"
      code << "    public boolean anyDeleted() {\n"
      code << "        return false;\n"
      code << "    }\n"
      code << "\n"
      code << "    @Override\n"
      code << "    public int deletedSize() {\n"
      code << "        return 0;\n"
      code << "    }\n"
    else
      code << "\n"
      code << @deleted_generator.generate_any_deleted_code
      code << "\n"
      code << @deleted_generator.generate_deleted_size_code
    end
  end

  def generate_deep_copy_code
    code = "\n"
    code << "    @Override\n"
    code << "    public #{@model_conf.name} deepCopy() {\n"
    code << "        return new #{@model_conf.name}().deepCopyFrom(this);\n"
    code << "    }\n"
  end
  
  def generate_deep_copy_from_code
    code = "\n"
    code << @deep_copy_from_generator.generate
  end

  def generate_to_string_code
    code = "\n"
    code << @to_string_generator.generate
  end

  def generate_class_suffix_code
    code = "\n"
    code << "}\n"
  end

end