require_relative 'const_generator'

class ConstsGenerator
  attr_reader :config, :model_conf

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
  end

  def generate
    code = ''
    code << generate_configured_consts
    code << generate_store_names
    code << generate_display_names
    code << generate_field_indices
  end

  private
  def generate_configured_consts
    code = ''
    consts = @model_conf.consts
    unless consts.empty?
      code << "\n"
      consts.each do |const|
        code << ConstGenerator.new(@model_conf, const).generate
      end
    end
    code
  end

  def generate_store_names
    code = ''
    fields = @model_conf.fields.filter { |field| field.store_field? or field.readonly? }
    unless fields.empty?
      code << "\n"
      fields.each do |field|
        code << "    public static final String #{field.store_name_const_name} = \"#{field.store_name}\";\n"
      end
    end
    code
  end

  def generate_display_names
    code = ''
    fields = @model_conf.fields.filter { |field| field.display_field? }
    unless fields.empty?
      code << "\n"
      fields.each do |field|
        code << "    public static final String #{field.display_name_const_name} = \"#{field.display_name}\";\n"
      end
    end
    code
  end

  def generate_field_indices
    code = ''
    fields = @model_conf.fields
    unless fields.empty?
      code << "\n"
      fields.each do |field|
        code << "    public static final int #{field.field_index_const_name} = #{field.index};\n"
      end
    end
    code
  end

end
