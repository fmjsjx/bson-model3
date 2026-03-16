require_relative 'imports_generator'
require_relative 'consts_generator'
require_relative 'store_data'
require_relative 'properties_generator'
require_relative 'clean_fields_generator'
require_relative 'append_field_updates_generator'
require_relative 'append_updated_data_generator'
require_relative 'to_display_data_generator'


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
              :to_display_data_generator
  
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
    # TODO generate other methods
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

  def generate_class_suffix_code
    "}\n"
  end

end