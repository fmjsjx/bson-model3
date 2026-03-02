require_relative 'imports_generator'

class ModelGenerator

  attr_reader :config,
              :model_conf,
              :imports_generator
  
  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @imports_generator = ImportsGenerator.new(@config, @model_conf)
    
  end

  def generate
    code = generate_class_prefix_code
    # TODO generate class content
    code << generate_class_suffix_code
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
    "public class #{@model_conf.name} extends #{generic_super_type} {\n"
  end

  def generic_super_type
    case @model_conf.type
    when 'object'
      "AbstractObjectModel<#{@model_conf.name}>"
    when 'root'
      "AbstractRootModel<#{@model_conf.name}>"
    else
      raise ArgumentError, "Unknown model type: #{@model_conf.type}"
    end
  end

  def generate_class_suffix_code
    "}\n"
  end

end