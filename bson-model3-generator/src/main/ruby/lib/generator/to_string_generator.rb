class ToStringGenerator

  attr_reader :config, :model_conf, :field_confs

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @field_confs = model_conf.fields
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    public String toString() {\n"
    if field_confs.empty?
      code << "        return \"#{@model_conf.name}()\";\n"
    else
      code << "        return \"#{@model_conf.name}(#{@field_confs[0].name}=\" + #{@field_confs[0].getter_name}() +\n"
      field_confs[1..].each do |field_conf|
        code << "                \", #{field_conf.name}=\" + #{field_conf.getter_name}() +\n"
      end
      code << "                \")\";\n"
    end
    code << "    }\n"
  end

end
